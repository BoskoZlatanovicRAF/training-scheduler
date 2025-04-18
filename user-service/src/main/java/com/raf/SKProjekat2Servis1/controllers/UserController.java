    package com.raf.SKProjekat2Servis1.controllers;

    import com.raf.SKProjekat2Servis1.domain.Korisnik;
    import com.raf.SKProjekat2Servis1.domain.KorisnikTrening;
    import com.raf.SKProjekat2Servis1.domain.Token;
    import com.raf.SKProjekat2Servis1.dto.KorisnikCreateDto;
    import com.raf.SKProjekat2Servis1.dto.KorisnikDto;
    import com.raf.SKProjekat2Servis1.dto.TokenRequestDto;
    import com.raf.SKProjekat2Servis1.dto.TokenResponseDto;
    import com.raf.SKProjekat2Servis1.dto.queueDto.KorisnikResetQueueDto;
    import com.raf.SKProjekat2Servis1.repository.KorisnikTreningRepository;
    import com.raf.SKProjekat2Servis1.repository.TokenRepository;
    import com.raf.SKProjekat2Servis1.restServicesConfig.NotificationService;
    import com.raf.SKProjekat2Servis1.security.CheckSecurity;
    import com.raf.SKProjekat2Servis1.services.implementations.JmsServiceImplementation;
    import com.raf.SKProjekat2Servis1.services.implementations.TokenServiceImpl;
    import com.raf.SKProjekat2Servis1.services.implementations.UserServiceImplementation;
    import io.jsonwebtoken.Claims;
    import io.swagger.models.auth.In;
    import lombok.AllArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpMethod;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.util.UriComponentsBuilder;
    import org.webjars.NotFoundException;

    import java.util.List;
    import java.util.Optional;

    @AllArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
        private final UserServiceImplementation userService;
        private final JmsServiceImplementation jmsService;
        private final TokenRepository tokenRepository;
        private final TokenServiceImpl tokenServiceImpl;

        @Autowired
        private final RestTemplate notificationRestService;
        private KorisnikTreningRepository korisnikTreningRepository;

        @PostMapping("/registerManager")
        public ResponseEntity<KorisnikDto> createManager( @RequestBody KorisnikCreateDto dto) {
            KorisnikDto createdEmployee = userService.register(dto,"Manager");
            userService.createManagerSala(createdEmployee.getIdCard(),dto.getSalaId());
            jmsService.sendRegistrationMessage(dto.getIme(), dto.getPrezime(),dto.getUsername(), createdEmployee.getTipKorisnika());
            return new ResponseEntity<KorisnikDto>(createdEmployee, HttpStatus.CREATED);
        }

        @PostMapping("/registerKorisnik")
        public ResponseEntity<KorisnikDto> createUser( @RequestBody KorisnikCreateDto dto) {
            KorisnikDto createdEmployee = userService.register(dto,"Standard");

            jmsService.sendRegistrationMessage(dto.getIme(), dto.getPrezime(),dto.getUsername(), createdEmployee.getTipKorisnika());
            return new ResponseEntity<KorisnikDto>(createdEmployee, HttpStatus.CREATED);
        }




        @PostMapping("/login")
        public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto dto) {
            TokenResponseDto responseDto = userService.login(dto);

            return new ResponseEntity<TokenResponseDto>(responseDto, HttpStatus.OK);
        }

        @GetMapping("/test")
        @CheckSecurity(roles = {"Standard"})
        public ResponseEntity<String> test(@RequestHeader("Authorization") String authorization) {

            return new ResponseEntity<String>("RADIIII", HttpStatus.OK);

        }
        @GetMapping("/activate")
        public ResponseEntity<String> activateUser(@RequestParam("token") String token) {

            userService.activate(token);

            return new ResponseEntity<String>("User activated", HttpStatus.OK);
        }

        @PostMapping("/requestNewPassword")
        public ResponseEntity<String> requestNewPassword(@RequestHeader("Authorization") String authorization) {
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return new ResponseEntity<>("Invalid Authorization header", HttpStatus.BAD_REQUEST);
            }

            // Extract the token from the Authorization header
            String token = authorization.substring(7);
            Claims claims = tokenServiceImpl.parse(token);
            Integer id = claims.get("id", Integer.class);

            KorisnikDto user = userService.findUserByIdCard(id);

            jmsService.sendNewPasswordMessage(user.getUsername(), user.getIdCard(), user.getTipKorisnika());


            return new ResponseEntity<String>("Mail for password sent", HttpStatus.OK);
        }

        @PostMapping("/resetPassword")
        public ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String authorization,@RequestParam("footprint") String footprint ,@RequestBody String newPassword) {

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                return new ResponseEntity<>("Invalid Authorization header", HttpStatus.BAD_REQUEST);
            }

            // Extract the token from the Authorization header
            String token = authorization.substring(7);
            Claims claims = tokenServiceImpl.parse(token);
            Integer id = claims.get("id", Integer.class);

            return new ResponseEntity<String>("Mail for password sent", HttpStatus.OK);
        }

        @GetMapping("/getManagerSala")
        public ResponseEntity<Integer> getManagerSala(@RequestParam Integer idCard) {
            Integer sala = userService.findMenagerSalaByIdCard(idCard);
            return new ResponseEntity<Integer>(sala, HttpStatus.OK);
        }

    // Dodao da vraca broj treninga za korisnika
    @PostMapping("/getBrojTreningaZaKorisnika")
    public ResponseEntity<Integer> getBrojTreningaZaKorisnika(@RequestParam("id") Integer id, @RequestParam("operator") String operator) {
        Optional<KorisnikTrening> korisnikTrening = korisnikTreningRepository.findByKorisnikIdCard(id);
        if (korisnikTrening.isPresent()) {
            if(operator.equals("inc")) {
                korisnikTrening.get().setBrojTreninga(korisnikTrening.get().getBrojTreninga() + 1);
                //Ovo će automatski vratiti broj treninga ako postoji, ili 0 ako Optional objekat ne sadrži vrednost.
                korisnikTreningRepository.save(korisnikTrening.get());
            } else if (operator.equals("dec")) {
                korisnikTrening.get().setBrojTreninga(korisnikTrening.get().getBrojTreninga() - 1);
                //Ovo će automatski vratiti broj treninga ako postoji, ili 0 ako Optional objekat ne sadrži vrednost.
                korisnikTreningRepository.save(korisnikTrening.get());
            }
            return new ResponseEntity<>(korisnikTrening.get().getBrojTreninga(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBrojTreninga")
    public ResponseEntity<Integer> getBrojTreninga(@RequestParam("korisnikId") Integer id) {
        Optional<KorisnikTrening> korisnikTrening = korisnikTreningRepository.findByKorisnikIdCard(id);
        if (korisnikTrening.isPresent()) {
            return new ResponseEntity<>(korisnikTrening.get().getBrojTreninga(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getKorisnik")
    @CheckSecurity(roles = {"Standard", "admin", "manager","Manager","Admin","standard"})
    public ResponseEntity<KorisnikDto> getKorisnik(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        KorisnikDto korisnikDto = userService.getKorisnikFromToken(authorization.substring(7));

        return new ResponseEntity<>(korisnikDto, HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    @CheckSecurity(roles = {"Standard", "admin", "manager","Manager","standard","Admin"})
    public ResponseEntity<KorisnikDto> updateUser(@RequestHeader("Authorization") String authorization, @RequestBody KorisnikDto dto) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        KorisnikDto korisnikDto = userService.updateUser(dto);

        return new ResponseEntity<>(korisnikDto, HttpStatus.OK);
    }

    @PostMapping("/deactivate")
    @CheckSecurity(roles = {"Standard", "admin", "manager","Manager","standard","Admin"})
    public ResponseEntity<KorisnikDto> deactivate(@RequestHeader("Authorization") String authorization, @RequestBody KorisnikDto dto) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

       userService.deactivate(dto.getUsername());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/activateAdmin")
    @CheckSecurity(roles = {"Standard", "admin", "manager","Manager","standard","Admin"})
    public ResponseEntity<KorisnikDto> activate(@RequestHeader("Authorization") String authorization, @RequestBody KorisnikDto dto) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        userService.activateAdmin(dto.getUsername());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/")
    @CheckSecurity(roles = {"Admin"})
   public ResponseEntity<List<KorisnikDto>> allUsers(@RequestHeader("Authorization") String authorization) {
        List<KorisnikDto> userDtos = userService.allUsers();
        return ResponseEntity.ok(userDtos);
   }

   @GetMapping("/getKorisnikByIdCard")
    public ResponseEntity<String> getKorisnikByIdCard(@RequestParam("idCard") Integer idCard) {
        KorisnikDto korisnikDto = userService.findUserByIdCard(idCard);
        return new ResponseEntity<String>(korisnikDto.getUsername(), HttpStatus.OK);
    }

    @GetMapping("/getManagerbySala")
    public ResponseEntity<Integer> getManagerbySala(@RequestParam("salaId") Integer salaId) {
        Integer korisnikDto = userService.getManagerbySala(salaId);
        return new ResponseEntity<Integer>(korisnikDto, HttpStatus.OK);
    }
}

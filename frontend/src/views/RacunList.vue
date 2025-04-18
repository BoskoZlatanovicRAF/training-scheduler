<template>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">Sala Naziv</th>
          <th scope="col">Trening Naziv</th>
          <th scope="col">Cena</th>
          <th scope="col">Pocetak Treninga</th>
          <th scope="col">Kraj Treninga</th>
          <th scope="col">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="racun in racuni" :key="racun.treningNaziv">
          <td>{{ racun.salaNaziv }}</td>
          <td>{{ racun.treningNaziv }}</td>
          <td>{{ racun.cena }}</td>
          <td>{{ racun.pocetakTreninga }}</td>
          <td>{{ racun.krajTreninga }}</td>
          <td>
            <button class="btn btn-danger" @click="cancel(racun.terminId)">Cancel</button>
          </td>
        </tr>
      </tbody>
    </table>
</template>

<script>
import {mapState,mapActions} from "vuex";
export default {
  data() {
    return {
      racuni: [],
    };
  },
  computed: {
    ...mapState([
      'token', 'currentUser'
    ]),

    token() {
      return this.$store.state.token;
    },


  },
  async mounted() {
    console.log("mouted",this.currentUser);
    fetch(`http://localhost:8086/api/racun/getRacun?korisnikId=${this.currentUser.idCard}`)
      .then(res => res.json())
      .then(data => {
        this.racuni = data;
        console.log(data);
        console.log(this.racuni);
      });
    console.log(this.currentUser);
  },

  methods:{
  async cancel(id) {
    fetch(`http://localhost:8086/api/treningKorisnik/obrisiKorisnikaSaTreninga?korisnikId=${this.currentUser.idCard}&terminId=${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    }) // Move this parenthesis up here
    .then(res => res.json())
    .then(data => {
      console.log("Uspesno otkazan racun");
      console.log(data);
    })          
  },
}

};
</script>
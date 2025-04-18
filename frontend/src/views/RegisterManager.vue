<script>
import router from "@/router";
import {mapActions, mapState} from "vuex";

export default {
  data() {
    return {
      korisnik: {
        username: '',
        ime: '',
        prezime: '',
        datum: null,
        tipKorisnika: '',
        password: '',
        salaId: '',
      },
      salaList: [],
      selectedSalaName: '',
      finalValue: 1,
    };
  },
  computed:{
      ...mapState([
        'sale'
      ]),
    
  },
  methods: {
    selectSala(item) {
      this.selectedSalaName = item.text;
      this.korisnik.salaId = item.value;
      this.finalValue = item.value;
      console.log("salaId",this.korisnik.salaId);
      console.log("item",item);
    },
    ...mapActions([
      'registerManager','fetchSale'
    ]),
    onSubmit() {
      console.log("salaId",this.korisnik.salaId);
      console.log('Form submitted:', this.korisnik);
      // Add logic to handle form submission
      this.korisnik.salaId = this.finalValue;
      console.log("salaId",this.korisnik.salaId);
      console.log("finalValue",this.finalValue);
      this.registerManager(this.korisnik);
      router.push({ name: 'login' });
    }
  },
  async mounted() {
    await this.fetchSale();

    for (let i = 0; i < this.sale.length; i++) {
      this.salaList.push({
        value: this.sale[i].id.toString(),
        text: this.sale[i].naziv
      });
    }
    console.log(this.salaList);
    console.log(this.sale);
  },

};
</script>

<template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Register Korisnik</h5>
            <form @submit.prevent="onSubmit">
              <div class="mb-3">
                <label for="username" class="form-label">Email</label>
                <input type="text" class="form-control" id="username" v-model="korisnik.username" required>
              </div>
              <div class="mb-3">
                <label for="ime" class="form-label">Ime</label>
                <input type="text" class="form-control" id="ime" v-model="korisnik.ime" required>
              </div>
              <div class="mb-3">
                <label for="prezime" class="form-label">Prezime</label>
                <input type="text" class="form-control" id="prezime" v-model="korisnik.prezime" required>
              </div>
              <div class="mb-3">
                <label for="datum" class="form-label">Datum rodjenja</label>
                <input type="date" class="form-control" id="datum" v-model="korisnik.datum">
              </div>
              <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" v-model="korisnik.password" required>
              </div>
              <div class="mb-3 ">
                <label for="Sala" class="form-label">Sala</label>
                <div class="order-section">
                  <b-dropdown :text="selectedSalaName" v-model="korisnik.salaId" variant="outline-secondary">
                    <b-dropdown-item
                        v-for="item in salaList"
                        :key="item.value"
                        @click="selectSala(item)"
                    >
                      {{ item.text }}
                    </b-dropdown-item>
                  </b-dropdown>
                </div>
              </div>
              <button type="submit" class="btn btn-primary">Submit</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>

</style>
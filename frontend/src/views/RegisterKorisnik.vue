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
              <button type="submit" class="btn btn-primary">Submit</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
        salaId: null,
      },
      salaList: [],
    };
  },
  computed:{
    ...mapState([
      'sale'
    ]),
  },
  methods: {
    ...mapActions([
      'registerUser','fetchSale'
    ]),
    onSubmit() {
      console.log('Form submitted:', this.korisnik);
      // Add logic to handle form submission
      this.registerUser(this.korisnik);
      this.$router.push({ name: 'login' });
    }
  },
  async mounted() {
    await this.fetchSale();

    for (let i = 0; i < this.sale.length; i++) {
      this.salaList.push({
        value: this.sale[i].id,
        name: this.sale[i].naziv
      });
    }
  }
};
</script>
<template>
    <div class="card">
      <div class="card-header">
        <h1>Add Termin</h1>
      </div>
      <div class="card-body">
        <b-form @submit.prevent>
            <b-form-group label="Starting Date and Time">
                <b-form-input type="datetime-local" v-model="termin.startDatetime"></b-form-input>
            </b-form-group>
            <b-form-group label="Ending Date and Time">
                <b-form-input type="datetime-local" v-model="termin.endDatetime"></b-form-input>
            </b-form-group>
            <b-form-group label="Trening">
                <b-form-select v-model="termin.trening" :options="treningOptions"></b-form-select>
            </b-form-group>
            <b-form-group label="Trener">
                <b-form-input type="text" v-model="termin.trener"></b-form-input>
            </b-form-group>
            <b-form-group label="Tezina Treninga">
                <b-form-input type="number" min="1" max="10" v-model.number="termin.terminOcena"></b-form-input>
            </b-form-group>
          <b-button class = "mt-3" type="submit" variant="primary" @click="addTermin">Add Termin</b-button>
        </b-form>
      </div>
    </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
export default {
    data() {
        return {
        termin: {
            datetime: '',
            trening: ''
        },
        treningOptions: []
        };
    },
    computed: {
        ...mapState([
        'treninge', 'currentUser'
        ])
    },
    methods: {
        ...mapActions([
        'fetchTreninge'
        ]),
        watch: {
            treninge: {
                handler() {
                    this.treninge.forEach(trening => {
                        this.treningOptions.push({
                            text: trening.naziv,
                            value: trening.naziv
                        });
                    });
                },
                immediate: true
            }
        },
        async addTermin() {
            const terminDto = {
                treningSala: this.currentUser.salaId,
                termin: this.termin.startDatetime,
                terminOcena: this.termin.terminOcena,
                trener: this.termin.trener,
                trening: this.termin.trening,
                kraj: this.termin.endDatetime
            };

        try {
            const response = await fetch('http://localhost:8086/api/termini/addTermin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(terminDto)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    }
    },
    async mounted() {
        let treningOptions = [];
        await this.fetchTreninge();
        this.treninge.forEach(trening => {
            this.treningOptions.push({
                text: trening.naziv,
                value: trening.naziv
        });
    });

    },

};
</script>
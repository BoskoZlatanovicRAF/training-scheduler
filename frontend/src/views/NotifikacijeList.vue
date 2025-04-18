<template>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">Tip Notifikacije</th>
          <th scope="col">Datum</th>
          <th scope="col" v-if="currentUser.tipKorisnika === 'Admin'">Tip Korisnika</th>
          <th scope="col" v-if="currentUser.tipKorisnika === 'Admin'">Email</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="notification in notifications" :key="notification.id">
          <td>{{ notification.tipNotifikacije }}</td>
          <td>{{ notification.datum }}</td>
          <td v-if="currentUser.tipKorisnika === 'Admin'">{{ notification.tipKorisnika }}</td>
          <td v-if="currentUser.tipKorisnika === 'Admin'">{{ notification.email }}</td>
        </tr>
      </tbody>
    </table>
  </template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      notifications: [],
    };
  },
  computed: {
    ...mapState(['currentUser']),
  },
  async mounted() {
    let endpoint;

    if (this.currentUser.tipKorisnika === 'Admin') {
      endpoint = 'http://localhost:8085/api/notification/';
    } else {
      endpoint = `http://localhost:8085/api/notification/currentNotification?username=${this.currentUser.username}`;
    }

    const response = await fetch(endpoint);
    if (response.ok) {
      this.notifications = await response.json();
    }
  },
};
</script>
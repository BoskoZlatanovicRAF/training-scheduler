<template>
    <ejs-schedule :currentView='currentView' height='550px' :selectedDate='selectedDate' :group='group' :eventSettings='eventSettings' readonly='true' @popupOpen="onPopupOpen">
      <e-views>
        <e-view option='TimelineDay' displayName="Day"></e-view>
        <e-view option='TimelineWeek' displayName="Week"></e-view>
      </e-views>
      <e-resources>
        <e-resource field="RoomId" title="SALA" name="Rooms" :dataSource="roomDataSource" textField="RoomText" idField="Id" colorField="RoomColor" groupIDField="GroupId">
      </e-resource>
      </e-resources>
    </ejs-schedule>
</template>
  
<script setup>
  import { provide, ref, computed } from "vue";
  import {
    ScheduleComponent as EjsSchedule, ViewsDirective as EViews, ViewDirective as EView,
    ResourcesDirective as EResources, ResourceDirective as EResource, TimelineViews, TimelineMonth
  } from "@syncfusion/ej2-vue-schedule";

  import { useStore } from 'vuex';
  const store = useStore();

  provide('schedule', [TimelineViews, TimelineMonth]);
  const dataSource = ref([]); 
  

  const currentUser = computed(() => store.state.currentUser);
  console.log("currentUser",currentUser);
  const currentView = ref('TimelineWeek');
  const selectedDate = ref(new Date());
  const group = ref({
        resources: ['Rooms'],
        byDate: true,
        allowGroupEdit: false,
    });
  
const eventSettings = ref({ // Making eventSettings a reactive object
  dataSource: dataSource.value,
  fields: {
    Subject: {name: 'Subject', default: 'No Title'},
    RoomId: {name: 'RoomId', default: 1},
    startTime: {name: 'StartTime'},
    endTime: {name: 'EndTime'},
    
  }
});

const onPopupOpen = (args) => {
  if (args.type === 'QuickInfo') {
    const quickInfoPopup = args.element;
    const eventDetails = args.data; // The event data is available here
    console.log("eventDetails",eventDetails);
    const termin = eventDetails.Id;
    const korisnikId = currentUser.value.idCard;
    console.log("termin i korisnikId",termin,korisnikId);
    // Create a function to handle the Cancel button click
    const handleCancelClick = () => {
      // Perform the DELETE request using fetch
      fetch(`http://localhost:8086/api/treningKorisnik/dodajKorisnikaNaTrening`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          korisnikId: korisnikId,
          termin: termin,
    }),
        // Additional options like headers, body, etc. might be needed depending on your API
      })
      .then(response => {
        // Handle response
        console.log('Zakazan termin', response);
        // Refresh the Scheduler or remove the event from the UI as needed
        router.push({ name: 'racuni' });

      })
      .catch(error => {
        // Handle errors
        console.error('Neuspesno zakazivanje treninga', error);
      });
    };

    // Check if the custom content is already there
  if (!quickInfoPopup.querySelector('.custom-info')) {
    // Add event details
      const eventSubject = document.createElement('div');
      eventSubject.textContent = `Subject: ${eventDetails.Subject}`;
      eventSubject.className = 'custom-info';

      const eventStartTime = document.createElement('div');
      eventStartTime.textContent = `Start: ${eventDetails.StartTime.toLocaleString()}`;
      eventStartTime.className = 'custom-info';

      const eventEndTime = document.createElement('div');
      eventEndTime.textContent = `End: ${eventDetails.EndTime.toLocaleString()}`;
      eventEndTime.className = 'custom-info';

      const eventPopunjenost = document.createElement('div');
      eventPopunjenost.textContent = `Popunjenost: ${eventDetails.popunjenost}`;
      eventPopunjenost.className = 'custom-info';

      const eventMaxKapacitet = document.createElement('div');
      eventMaxKapacitet.textContent = `Max Kapacitet: ${eventDetails.maxKapacitet}`;
      eventMaxKapacitet.className = 'custom-info';

      const eventCena = document.createElement('div');
      eventCena.textContent = `Cena: ${eventDetails.cena}`;
      eventCena.className = 'custom-info';

      

      // Add
      if(eventDetails.vecZakazan){
        const eventVecZakazan = document.createElement('div');
        eventVecZakazan.textContent = `Vec ste zakazali ovaj termin`;
        eventVecZakazan.className = 'custom-info';
        quickInfoPopup.querySelector('.e-popup-content').append(eventVecZakazan);
      }
      else{
        const cancelButton = document.createElement('button');
        cancelButton.textContent = 'JOIN';
        cancelButton.className = 'custom-cancel-button';
        cancelButton.onclick = handleCancelClick; // Attach the click handler

        // Append the new elements
        const popupFooter = quickInfoPopup.querySelector('.e-popup-footer');
        popupFooter.before(eventSubject, eventStartTime, eventEndTime, eventPopunjenost, eventMaxKapacitet, eventCena, cancelButton);
        
        const customCancelButton = quickInfoPopup.querySelector('.custom-cancel-button');
        customCancelButton.style.backgroundColor = '#4CAF50'; // Green
        customCancelButton.style.border = 'none';
        customCancelButton.style.color = 'white';
        customCancelButton.style.padding = '15px 32px';
        customCancelButton.style.textAlign = 'center';
        customCancelButton.style.textDecoration = 'none';
        customCancelButton.style.display = 'inline-block';
        customCancelButton.style.fontSize = '16px';
        customCancelButton.style.margin = '4px 2px';
        customCancelButton.style.cursor = 'pointer';
        
      }

      // Add some minimalistic styles
      const customInfoElements = quickInfoPopup.querySelectorAll('.custom-info');
      customInfoElements.forEach((element) => {
        element.style.marginBottom = '10px';
        element.style.fontFamily = 'Arial, sans-serif';
        element.style.fontSize = '14px';
      });

      
    }
  }
};
const roomDataSource = ref([]);
import { onMounted } from 'vue';
import router from "@/router";

onMounted(() => {
    console.log("currentUser",currentUser);
    store.dispatch('fetchSale').then(() => {
      const sales = store.getters.setSale;
    
      roomDataSource.value = sales.map((sala, index) => ({
        RoomText: sala.naziv,
        Id: sala.id,
        GroupId: sala.id,
        RoomColor: '#' + Math.floor(Math.random()*16777215).toString(16)
      }));
      console.log("roomDataSource",roomDataSource.value);
    });
    fetch(`http://localhost:8086/api/termini/current?korisnikId=${currentUser.value.idCard}`).then(res => res.json()).then(data => {
    console.log(data);
    dataSource.value = data.map(event => ({
        // Map your event data to the format required by dataSource
        // For example:
        Id: event.id,
        RoomId: event.treningSala,
        Subject: event.trening,
        StartTime: new Date(event.termin),
        EndTime: new Date(event.kraj),
        popunjenost: event.popunjenost,
        maxKapacitet: event.maxKapacitet,
        cena: event.cena,
        vecZakazan: event.vecZakazan,
        // Add other event properties as needed
    }));
    eventSettings.value = { ...eventSettings.value, dataSource: dataSource.value };
    console.log("eventSettings",eventSettings.value);
    });



  });
  
  
</script>
 <style>
 @import '~@syncfusion/ej2-base/styles/material.css';
 @import '~@syncfusion/ej2-buttons/styles/material.css';
 @import '~@syncfusion/ej2-calendars/styles/material.css';
 @import '~@syncfusion/ej2-dropdowns/styles/material.css';
 @import '~@syncfusion/ej2-inputs/styles/material.css';
 @import '~@syncfusion/ej2-navigations/styles/material.css';
 @import '~@syncfusion/ej2-popups/styles/material.css';
 @import '~@syncfusion/ej2-vue-schedule/styles/material.css';
 </style>
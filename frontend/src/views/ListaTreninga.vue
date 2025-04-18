<template>
  <ejs-schedule :eventRendered="eventRendered" :currentView='currentView' height='550px' :selectedDate='selectedDate' :eventSettings='eventSettings' colorField="Color" @popupOpen="onPopupOpen">
    <e-views>
      <e-view option='Day' displayName="Day"></e-view>
      <e-view option='Week' displayName="Week"></e-view>
    </e-views>
  </ejs-schedule>
</template>
<script setup>
import { provide, ref, onMounted } from "vue";
import { ScheduleComponent as EjsSchedule, ViewsDirective as EViews, ViewDirective as EView, Day, Week } from "@syncfusion/ej2-vue-schedule";
import { useStore } from 'vuex';
provide('schedule', [Day, Week]);

const dataSource = ref([]); 
const currentView = ref('Week');
const selectedDate = ref(new Date()); // Change this line
const eventSettings = ref({
  dataSource: dataSource.value,
  fields: {
    Subject: {name: 'Subject', default: 'No Title'},
    startTime: {name: 'StartTime'},
    endTime: {name: 'EndTime'},
    Color: {name: 'Color'}
  }
});
const store = useStore();



const eventRendered = (args) => {
  args.element.style.backgroundColor = args.data.Color;
};


const onPopupOpen = (args) => {
  if (args.type === 'QuickInfo') {
    const quickInfoPopup = args.element;
    const currentUser = store.state.currentUser;
    const eventDetails = args.data; // The event data is available here
    console.log("currentUser",currentUser);
    console.log("eventDetails",eventDetails);
    // Create a function to handle the Cancel button click
    const handleCancelClick = () => {
      // Perform the DELETE request using fetch
      fetch(`http://localhost:8086/api/termini/delete?id=${eventDetails.Id}&managerId=${currentUser.idCard}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        },
        // Additional options like headers, body, etc. might be needed depending on your API
      })
      .then(response => {
        // Handle response
        console.log('Termin otkazan, poslate notifikacije svim korisnicima', response);
        // Refresh the Scheduler or remove the event from the UI as needed
        location.reload();
      })
      .catch(error => {
        // Handle errors
        console.error('Error cancelling event', error);
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

      // Add
      const cancelButton = document.createElement('button');
      cancelButton.textContent = 'CANCEL';
      cancelButton.className = 'custom-cancel-button';
      cancelButton.onclick = handleCancelClick; // Attach the click handler

      // Append the new elements
      const popupFooter = quickInfoPopup.querySelector('.e-popup-footer');
      popupFooter.before(eventSubject, eventStartTime, eventEndTime, cancelButton);

      // Add some minimalistic styles
      const customInfoElements = quickInfoPopup.querySelectorAll('.custom-info');
      customInfoElements.forEach((element) => {
        element.style.marginBottom = '10px';
        element.style.fontFamily = 'Arial, sans-serif';
        element.style.fontSize = '14px';
      });

      const customCancelButton = quickInfoPopup.querySelector('.custom-cancel-button');
      customCancelButton.style.backgroundColor = '#FF0000'; // Green
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
  }
};



onMounted(() => {
  const currentUser = store.state.currentUser;
  const salaId = currentUser.salaId;

  fetch(`http://localhost:8086/api/termini/current?korisnikId=${currentUser.idCard}`)
    .then(res => res.json())
    .then(data => {
      if (Array.isArray(data)) {
        dataSource.value = data
          .filter(event => event.treningSala === salaId)
          .map(event => {
            const color = '#' + Math.floor(Math.random()*16777215).toString(16); // Generate random color
            return{
              Id: event.id,
              Subject: event.trening,
              StartTime: new Date(event.termin),
              EndTime: new Date(event.kraj),
              Color: color // Add random color
            };     
          });
      } else if (data.treningSala === salaId) {
        const color = '#' + Math.floor(Math.random()*16777215).toString(16); // Add random color
        dataSource.value = [{
          Id: data.id,
          Subject: data.trening,
          StartTime: new Date(data.termin),
          EndTime: new Date(data.kraj),
          Color: color

        }];
      }

      eventSettings.value = { ...eventSettings.value, dataSource: dataSource.value };
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
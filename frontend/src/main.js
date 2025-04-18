import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {registerLicense} from "@syncfusion/ej2-base";
import {SchedulePlugin} from "@syncfusion/ej2-vue-schedule";

registerLicense("Ngo9BigBOggjHTQxAR8/V1NAaF5cWWZCfEx3Rnxbf1x0ZFJMZFRbR3BPMyBoS35RckViWHteeXdVQmdUVER+");
import BootstrapVueNext from 'bootstrap-vue-next'

// Optional, since every component imports their Bootstrap functionality
// the following line is not necessary
// import 'bootstrap'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-next/dist/bootstrap-vue-next.css'

const app = createApp(App)
app.use(BootstrapVueNext)

app.use(store).use(router).mount('#app')

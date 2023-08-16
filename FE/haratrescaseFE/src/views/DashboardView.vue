<template>
  <div>
    <h1>Task Dashboard Page</h1>
    
    <div v-if="endpointData">
      <h2>Data from Endpoint 1</h2>
      <pre>{{ endpointData.endpoint1 }}</pre>
    </div>
    
    <div v-if="error">
      <p>Error fetching data: {{ error }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'DashboardView',
  data() {
    return {
      endpointData: null,
      error: null
    };
  },
  methods: {
    async fetchData(endpoint) {
      const jwt = localStorage.getItem('jwt');
      
      if (!jwt) {
        this.error = 'No JWT found!';
        return;
      }
      
      try {
        const response = await axios.get(`http://localhost:8081/${endpoint}`, {
          headers: {
            'Authorization': `Bearer ${jwt}`
          }
        });
        return response.data;
      } catch (err) {
        this.error = err.response ? err.response.data : err.message;
      }
    }
  },
  async created() {
    this.endpointData = {
      endpoint1: await this.fetchData('user/mytasks')
    };
  }
};
</script>

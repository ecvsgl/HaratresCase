<template>
  <body>
    <form class="form-signin">
      <h3>Login</h3>

      <div class="form-group">
        <label>Username</label>
        <input type="text" class="form-control" v-model="userName" placeholder="Enter Username"/>
      </div>

      <div class="form-group">
        <label>Password</label>
        <input type="password" class="form-control" v-model="userPassword" placeholder="Enter Password"/>
      </div>

      <button id="loginButton" class="btn btn-primary btn-block" @click.prevent="handleLogin">Login</button>
      <button id= "signUpButton" class="btn btn-primary btn-block" @click.prevent="handleSignUp">Sign Up</button>
    </form>

  </body>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'Login',
    data(){
      return {
        userName: '',
        userPassword: '',
      }
    },
    methods :{
      async handleLogin(){
        try{
          const response = await axios.post('http://localhost:8081/auth/login',{
          userName: this.userName,
          userPassword: this.userPassword
        });
        if(response.data.jwt && response.data.jwt !== ''){
          localStorage.setItem('jwt', response.data.jwt);
          if(
            response.data.userResponse && 
            response.data.userResponse.authorities &&
            Array.isArray(response.data.userResponse.authorities) &&
            response.data.userResponse.authorities.some(auth => auth.authority === 'ADMIN')){
              this.$router.push('/admindashboard');
            } else{
              this.$router.push('/dashboard');
            }
        } else{
          alert("Invalid credidentials.");
        }
        } catch (error){
          console.error("Error logging in: ", error.response ? error.response.data : error.message);
        }
      },
      async handleSignUp(){
        try{
          const response = await axios.post('http://localhost:8081/auth/register',{
          userName: this.userName,
          userPassword: this.userPassword
        });
        if(response.data){
          alert("User created for: " + response.data.userName);
        }
      } catch (error){
        alert("Invalid username. Please provide another.")
        console.error("Error signing up: ", error.response ? error.response.data : error.message);
      }
      }
    }
  }
</script>
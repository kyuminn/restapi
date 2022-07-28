<template>
  <div>
    <h2>hey</h2>
  <table>
    <thead>
      <th>email</th>
      <th>password</th>
    </thead>
    <tbody>
      <tr v-for="item in this.memberList">{{item.email}}</tr>
      <tr v-for="item in this.memberList">{{item.password}}</tr>
    </tbody>
  </table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "table.vue",
  memberList: [],
  mounted() {
    axios.get('/member')
      .then((res)=>{
        this.memberList = res.data;
      })
      .catch((err)=>{
        console.log(err);
      })
  }
}


</script>

<style scoped>

</style>

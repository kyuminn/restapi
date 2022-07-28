<template>
  <div>
    <table>
      <thead>
          <th>팀이름</th>
          <th>가입일</th>
      </thead>
      <tbody>
        <tr v-for="item in this.teamList">
          <td>{{item.name}}</td>
<!--          <td>{{item.estbdate}}</td>-->
          <td>{{ $moment(item.estbdate).format('YYYY-MM-DD')}}</td> <!--vue-moment 사용-->
        </tr>
      </tbody>
    </table>
    <button @click="excel">excel 다운로드</button>
  </div>
</template>

<script>
import axios from "axios";
import excelDownload from "../js/common";

export default {
  name: "TestTable.vue",
  data(){
    return {
      teamList : []
    }
  },
  mounted() {
    axios.get('http://localhost:8080/team')
      .then((res)=>{
        for(let i=0; i<res.data.length; i++){
          let year = res.data[i].estbdate[0];
          let month = res.data[i].estbdate[1];
          let day = res.data[i].estbdate[2];

          if(month.toString().length===1){
            month = '0'+month;
          }

          if(day.toString().length===1){
            day = '0'+day;
          }
          console.log(res.data[i].estbdate)
          // res.data[i].estbdate = res.data[i].estbdate[0]+'-'+res.data[i].estbdate[1]+'-'+res.data[i].estbdate[2]
          res.data[i].estbdate = year+'-'+month+'-'+day;

          console.log(res.data[i].estbdate)
        }
        this.teamList= res.data;
        console.log(JSON.stringify(res.data))
      })
      .catch((err)=>{
        console.log(err)
      })
  },
  methods:{
    excel(){
      excelDownload(this.teamList);
    }
  }

}


</script>

<style scoped>
    table {
      width: 300px;
      margin:auto;
    }

    table,tr,td {
      border: 1px solid black
    }
</style>

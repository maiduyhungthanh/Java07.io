const API_Singer = "http://localhost:1993/api/category";
let list_singer = [];
//Khai báo list singer
const singerAll = document.querySelector(".list_singer");




//lấy API Singer
const getListSinger = async () => {
   
        let res = await axios.get(API_Singer);
        list_singer = res.data;
        console.log(list_singer);
        let html = "";
        for (let i = 0; i < list_singer.length; i++) {
            let s = list_singer[i];
            html += `
            <div class="singerEl" style="margin:20px;"> <a href="/album|${s.id}" style="color:blueviolet;"><img src="${s.avatar}" alt="" style="width: 120px; height: 120px;border-radius: 50%;"><br>
            ${s.name}</a> </div>
            `
        }
        singerAll.innerHTML=html
}


getListSinger();
const API_SingerById = "http://localhost:1993/api/singer";
var link = location.href;
console.log(link)

var singer ;
const singerById = document.getElementById("singer-by-id")
const btnDel = document.getElementById("delete")
const nameEl = document.getElementById("nameEl")
const imgEl = document.getElementById("imgEl")
const editEl = document.getElementById("edit")

console.log(nameEl.innerHTML)
//lấy id
var x = [];
for (let i = link.length-1; i >= 0; i--) {
    if(link[i]=="C"){
    break;
}
x.push(link[i]);
}
const id = x.reverse().join("");
console.log(id)

//Lấy dữ liệu API
const getListSinger = async () => {
    let res = await axios.get(`${API_SingerById}/${id}`);
    singer = res.data;
    console.log(singer.name);
    editEl.innerHTML = `<a href="/singer-edit|${singer.id}">
    <p style="color: blueviolet;margin-right: -400px;font-size: 20px;">Chỉnh Sửa</p>
  </a>`
    console.log(editEl.innerHTML)
    nameEl.innerHTML = `Ca Sỹ : ${singer.name}`;
    imgEl.src = `${singer.avatar}`;
}

btnDel.addEventListener("click", async function () {

      let res = await axios.delete(`${API_SingerById}-delete/${id}`)
      console.log(res)
          window.location.href = "/singer"
})
getListSinger();
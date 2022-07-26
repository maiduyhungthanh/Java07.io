const singerEl = document.querySelectorAll(".singerEl")
const albumEl = document.querySelectorAll(".albumEl")
const avatarPreview = document.getElementById("avatar-preview")
const avatarEl = document.getElementById("avatarEl")
const mp3El = document.getElementById("mp3El")
const nameEl = document.getElementById("nameEl")
const btnSave = document.getElementById("btn-save")
const singer1 = document.getElementById("singer1")
const singer2 = document.getElementById("singer2")
const singer3 = document.getElementById("singer3")
const link_mp3 = document.getElementById("linkmp3")
const id_singer = document.getElementById("id_singer")
const id_album = document.getElementById("id_album")
//dropdown ngon ngu
const ngonngu = document.getElementById("ngonngu");
const table_ngonngu = document.getElementById("table-ngonngu");
ngonngu.addEventListener("click", function () {
  if (table_ngonngu.style.opacity == "0") {
    table_ngonngu.style.opacity = "1";
  } else {
    table_ngonngu.style.opacity = "0";
  }
})
//dropdown trang ca nhan
const canhan = document.getElementById("canhan");
canhan.addEventListener("click",function(){
if(table_anhdaidien.style.opacity=="0"){
table_anhdaidien.style.opacity="1";
}else{
table_anhdaidien.style.opacity="0";
}
})
//lấy API Album
let list_album = [];
const albumAll = document.querySelector(".album");
const getListAlbum = async () => {
  let res = await axios.get("http://localhost:1993/api/category");
  list_album = res.data;
  console.log(list_album);
  let html = `<h2 style="color:Red;font-size:20px;max-width: none;width: 110px;">Top Album</h2><br><br>`
  for (let i = 0; i < list_album.length; i++) {
    let s = list_album[i];
    html += `
            <div class="p2">
            <h3 style="color: greenyellow;width: 110px;max-width: none;">${s.name}</h3>
            <a href="/album|${s.id}">
            <img src="${s.avatar}" style="height: 110px;width: 110px;max-width: none;"></a>
            </div>
            `
  }
  albumAll.innerHTML = html;
}
getListAlbum();
//lấy API Người dùng
const anhdaidien = document.getElementById("anhdaidien")
const dangnhap = document.getElementById("dangnhap")
const dangky = document.getElementById("dangky")
const table_anhdaidien = document.getElementById("table-anhdaidien");
const getUser = async () => {
  let res = await axios.get("http://localhost:1993/tendangnhap");
  user = res.data;
  console.log(user);
  if (user.id != null) {
    anhdaidien.innerHTML = `   
                    <source srcset="${user.avatar}" type="image/webp"><img
                      src="${user.avatar}" alt="User name">                 
    `
    table_anhdaidien.innerHTML=`
    <li><a href="">Trang Cá Nhân</a></li>
    <li><a href="">Bài hát yêu thích</a></li>
    <li><a href="">Quản trị viên</a></li>
    `
    dangnhap.innerHTML = `
    <button href="##" class="nav-user-btn dropdown-btn" style="color: red;" title="${user.email}" type="button">
    ${user.firstName} ${user.lastName}
  </button>
    `
    dangky.innerHTML = `
    <a href="/logout"><button  class="nav-user-btn dropdown-btn" style="color: red;"title="Logout" type="button">
                Thoát
              </button></a>
    `
  }
}
getUser();
var listSinger = []
var listAlbum = []
const getSinger = async () => {
    try {
        let res = await axios.get("http://localhost:1993/api/singer")
        console.log(res.data);

        renderSinger(res.data)
    } catch (error) {
        console.log(error)
    }
}
const getAlbum = async () => {
    try {
        let res = await axios.get("http://localhost:1993/api/category")
        renderAlbum(res.data)
    } catch (error) {
        console.log(error)
    }
}
// chuyển từ mp3 ảnh sang link
const mp3API = file => {
    const formData = new FormData();
    formData.append("file", file);
    return axios.post(`http://localhost:1993/api/song/mp3`, formData);
}
// thêm singer nếu cập nhập mp3
mp3El.addEventListener("change", async function (event) {
    let r = await axios.get("http://localhost:1993/api/singer")
    console.log(r.data)
    for (let i = 0; i < r.data.length; i++) {
        if (singerEl[0].value == r.data[i].id ||
            singerEl[1].value == r.data[i].id ||
            singerEl[2].value == r.data[i].id) {
            listSinger.push(r.data[i].id)
        }
    }
    console.log(listSinger)
    let res = await axios.get("http://localhost:1993/api/category")
    for (let i = 0; i < res.data.length; i++) {
        if (albumEl[0].value == res.data[i].id ||
            albumEl[1].value == res.data[i].id) {
            listAlbum.push(res.data[i].id)
        }
    }
    id_singer.innerHTML += listSinger.join("/")
    id_album.innerHTML += listAlbum.join("/")
    let file = event.target.files[0]
    try {
        let res = await mp3API(file)
        console.log(res)
        link_mp3.innerHTML = `http://localhost:1993/api/song/mp3/${res.data}`
        console.log(link_mp3.innerHTML)

    } catch (error) {
        console.log(error)
    }
})
// chuyển từ file ảnh sang link
const uploadFileAPI = file => {
    const formData = new FormData();
    formData.append("file", file);
    return axios.post(`http://localhost:1993/api/song/image`, formData);
}
// thêm singer nếu cập nhập ảnh
avatarEl.addEventListener("change", async function (event) {
    let file = event.target.files[0]
    try {
        let res = await uploadFileAPI(file)
        console.log(res)
        avatarPreview.src = `http://localhost:1993/api/song/image/${res.data}`
        console.log(avatarPreview.src)
    } catch (error) {
        console.log(error)
    }
})
const renderSinger = arr => {
    let html = `<option value="123"  selected="selected">Không</option>`
    // let html =``
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.id}">${d.name}</option>`
    }
    for (let j = 0; j < singerEl.length; j++) {
        const s = singerEl[j]
        s.innerHTML = html;
    }
}
const renderAlbum = arr => {
    let html = `<option value="123"  selected="selected">Không</option>`
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.id}">${d.name}</option>`
    }
    for (let j = 0; j < albumEl.length; j++) {
        const s = albumEl[j]
        s.innerHTML = html;
    }
}
//Thêm Singer nếu không Update ảnh
btnSave.addEventListener("click", async function () {

    try {
        let res = await axios.post(`http://localhost:1993/api/song`,
            {
                name: nameEl.value,
                avatar: avatarPreview.src,
                mp3: link_mp3.innerHTML,
                lyric: id_singer.innerHTML+"/"+id_album.innerHTML
            })
        if (res.data) {
            window.location.href = "/"
        }
    } catch (error) {
        window.location.href = "/singer"
    }

})

const init = async () => {
    await getSinger(),
    await getAlbum()
}
init()

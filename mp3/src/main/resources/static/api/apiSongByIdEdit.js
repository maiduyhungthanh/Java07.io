const API_SongById = "http://localhost:1993/api/song";
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const singerEl = document.querySelectorAll(".singerEl")
const albumEl = document.querySelectorAll(".albumEl")
const btnSave = document.getElementById("btn-save")
const id_singer = document.getElementById("id_singer")
const id_album = document.getElementById("id_album")
var Singer_id = []
var Album_id = []
//Bang xep hang
const bxh = document.querySelector(".bxh")
const getBXH = async () => {
  let res = await axios.get("http://localhost:1993/api/topsong");
  var top = res.data;
  var html = ``
  for (let i = 0; i < top.length; i++) {
    var singer
    if(top[i].singers.length==1){
      singer =`<a href="http://localhost:1993/singer|${top[i].singers[0].id}"> ${top[i].singers[0].name}</a>`
    }else if (top[i].singers.length==2){
      singer =`<a href="http://localhost:1993/singer|${top[i].singers[0].id}"> ${top[i].singers[0].name}</a> 
             & <a href="http://localhost:1993/singer|${top[i].singers[1].id}"> ${top[i].singers[1].name}</a>`
    }else if (top[i].singers.length==3){
      singer =`<a href="http://localhost:1993/singer|${top[i].singers[0].id}"> ${top[i].singers[0].name}</a> 
      & <a href="http://localhost:1993/singer|${top[i].singers[1].id}"> ${top[i].singers[1].name}</a>
      & <a href="http://localhost:1993/singer|${top[i].singers[2].id}"> ${top[i].singers[2].name}</a>`
    }
  html +=`<li>
    <div class="top-cat-list__title purple">
    <a href="http://localhost:1993/song|${top[i].id}"style="color:pink"> ${top[i].name}</a> <span class="purple" style="color:red">Top ${i+1}</span>
    </div>
    <div class="top-cat-list__subtitle">
      ${singer}<span class="purple">+${top[i].view} view</span> 
    </div>
  </li>`       
  }
  bxh.innerHTML=html;
}
getBXH();
// tim kiem bai hat
const keyword = document.getElementById("keyword");
keyword.addEventListener("keydown",function(event){
  if(keyword.value != "" && event.key=="Enter"){
    window.location.href = `http://localhost:1993/song?keyword=${keyword.value}`
  }
})
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
var id_user;
//lấy API Người dùng
const anhdaidien = document.getElementById("anhdaidien")
const dangnhap = document.getElementById("dangnhap")
const dangky = document.getElementById("dangky")
const table_anhdaidien = document.getElementById("table-anhdaidien");
const getUser = async () => {
  let res = await axios.get("http://localhost:1993/tendangnhap");
  user = res.data;
  id_user = user.id;
  var checkRoles ;
  for (let i = 0; i < user.roles.length; i++) {
    if(user.roles[i].name=="ROLE_USER"&& user.roles[i].name!="ROLE_ADMIN"){
      checkRoles=1;
    }
    if(user.roles[i].name=="ROLE_ADMIN"){
      checkRoles=0;
      break;
    }
  }
  if (checkRoles==0) {
      anhdaidien.innerHTML = `   
                    <source srcset="${user.avatar}" type="image/webp"><img
                      src="${user.avatar}" alt="User name">                 
    `
      table_anhdaidien.innerHTML = `
    <li><a href="http://localhost:1993/user|${user.id}">Trang Cá Nhân</a></li>
    <li><a href="http://localhost:1993/userlikesong|${user.id}">Bài hát yêu thích</a></li>
    <li><a href="">Đổi Mật Khẩu</a></li>
    <li><a href="http://localhost:1993/quantrivien">Quản Trị Viên</a></li>
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
    }else if (checkRoles==1) {
      anhdaidien.innerHTML = `   
                    <source srcset="${user.avatar}" type="image/webp"><img
                      src="${user.avatar}" alt="User name">                 
    `
      table_anhdaidien.innerHTML = `
    <li><a href="http://localhost:1993/user|${user.id}">Trang Cá Nhân</a></li>
    <li><a href="http://localhost:1993/userlikesong|${user.id}">Bài hát yêu thích</a></li>
    <li><a href="">Đổi Mật Khẩu</a></li>
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
// lấy id Song
var link = location.href;
var x = [];
for (let i = link.length - 1; i >= 0; i--) {
    if (link[i] == "C") {
        break;
    }
    x.push(link[i]);
}
const ID = x.reverse().join("").replace('?', '');
console.log(ID)

//Lấy dữ liệu Song truyền vào view
const getSong = async ID => {
    try {
        let res = await axios.get(`http://localhost:1993/api/song/${ID}`)
        renderSong(res.data);

    } catch (error) {
        console.log(error)
    }
}
// thay đổi ảnh Song
const uploadFileAPI = file => {
    const formData = new FormData()
    formData.append("file", file)
    return axios.post(`http://localhost:1993/api/song/files/${ID}`, formData)
}
avatarEl.addEventListener("change", async function (event) {
    let file = event.target.files[0]
    try {
        let res = await uploadFileAPI(file)
        avatarPreview.src = `http://localhost:1993/api/song/files/${ID}/${res.data}`;
        console.log(`http://localhost:1993/api/song/files/${ID}/${res.data}`)
    } catch (error) {
    }
    console.log(file)
})

// lam select Singer
const getSinger = async () => {
    try {
        let res = await axios.get("http://localhost:1993/api/singer")
        console.log(res.data);

        renderSinger(res.data)
    } catch (error) {
        console.log(error)
    }
}
const renderSinger = arr => {
    let html = `<option value="123">Không</option>`
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.id}">${d.name}</option>`
    }
    for (let j = 0; j < singerEl.length; j++) {
        const s = singerEl[j]
        s.innerHTML = html;
        Singer_id.push(s.id);
    }
}

    //    let res_singer = await axios.get("http://localhost:1993/api/singer")
    //     for (let i = 0; i < res_singer.data.length; i++) {
    //         if (singerEl[0].value == res_singer.data[i].id ||
    //             singerEl[1].value == res_singer.data[i].id ||
    //             singerEl[2].value == res_singer.data[i].id) {
    //             listSinger.push(res_singer.data[i].id)
    //         }
    //     }
    //     let res_album = await axios.get("http://localhost:1993/api/category")
    //     for (let i = 0; i < res_album.data.length; i++) {
    //         if (albumEl[0].value == res_album.data[i].id ||
    //             albumEl[1].value == res_album.data[i].id) {
    //             listAlbum.push(res_album.data[i].id)
    //         }
    //     }
        // id_singer.innerHTML += listSinger.join("/")
        // id_album.innerHTML += listAlbum.join("/")


let count1 = 0;
let count2 = 0;
const renderSong = s => {
        nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`,
        s.singers.forEach(s => {
            singerEl[count1].value = s.id
            count1++;
        }),
        s.categorys.forEach(s => {
            albumEl[count2].value = s.id
            count2++;
        });
    btnSave.addEventListener("click", async function () {
        try {
            let res = await axios.post("http://localhost:1993/api/song-save", {
                id: ID,
                name: nameEl.value,
                avatar: avatarPreview.src,
                mp3:s.mp3,
                lyric: singerEl[0].value+"/"+singerEl[1].value+"/"+singerEl[2].value+"/"+albumEl[0].value+"/"+albumEl[1].value
            })
            console.log(res)
            if (res.data) {
                window.location.href = "/singer"
            }
        } catch (error) {
            window.location.href = "/singer"
        }
    })
}
const init = async () => {
    await getSinger()
    
    getSong(ID)
}
init()

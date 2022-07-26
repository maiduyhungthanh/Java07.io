const API_SongById = "http://localhost:1993/api/song";
const singerById = document.getElementById("singer-by-id")
const avatarEl = document.getElementById("avatarEl")
const avatarPreview = document.getElementById("avatar-preview")
const nameEl = document.getElementById("nameEl")
const singerEl = document.querySelectorAll(".singerEl")
const btnSave = document.getElementById("btn-save")
console.log(singerEl.length)
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
    let html = `<option value="">Không</option>`
    for (let i = 0; i < arr.length; i++) {
        const d = arr[i]
        html += `<option value="${d.name}">${d.name}</option>`
    }
    for (let j = 0; j < singerEl.length; j++) {
        const s = singerEl[j]
        s.innerHTML = html;
    }
}

let count = 0;
const renderSong = s => {
        nameEl.value = s.name,
        avatarPreview.src = `${s.avatar}`,

        s.singers.forEach(s => {
            singerEl[count].value = s.name
            count++;
        });

    btnSave.addEventListener("click", async function () {
        try {
            let res = await axios.post("http://localhost:1993/api/song-save", {
                id: ID,
                name: nameEl.value,
                avatar: avatarPreview.src,
                mp3:s.mp3
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

const users = document.getElementById("user-table")
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
//hien thi DS nguoi dung
const list_role = document.querySelectorAll(".table_role")
const list_href = document.querySelectorAll(".table_href")
const list_delete = document.querySelectorAll(".table_delete")
const list_email = document.querySelectorAll(".table_email")
const list_avatar = document.querySelectorAll(".table_avatar")
const list_state = document.querySelectorAll(".table_state")
const role = document.querySelectorAll(".role")
const getTableUser = async () => {
  try {
      let res_user = await axios.get("http://localhost:1993/api/users");
      solgUser = res_user.data.length
      renderUser(res_user.data);
  } catch (error) {
      console.log(error)
  }
}
const renderUser = s => {
  for (let i = 0; i < s.length; i++) {
    let u = s[i];
    if(u.roles.length==3){
      role[i].value="admin"
    }else if(u.roles.length==2){
      role[i].value="operator"
    }else if(u.roles.length==1){
      role[i].value="user"
    }
    list_avatar[i].innerHTML = `<label class="users-table__checkbox">
            <div class="categories-table-img">
                <img src="${u.avatar}" alt="">
            </div>
            </label>`
    list_email[i].innerHTML = `${u.email}`
    list_state[i].innerHTML = `<span class="badge-pending">${u.state}</span>`
  }
  for (let j = 0; j < list_href.length; j++) {
    list_href[j].addEventListener("click",async function(){  
      try {
        let res = await axios.post("http://localhost:1993/api/role-save", {
            id_user: s[j].id,
            value_role:role[j].value
        })
        console.log(res)
        if (res.data) {
            window.location.href = "/quantrivien"
        }
    } catch (error) {
        window.location.href = "/404"
    }
  })
  }
  for (let k = 0; k < list_href.length; k++) {
    list_delete[k].addEventListener("click",async function(){  
      var luachon = confirm(`Bạn chắc chắn muốn xóa ${s[k].email}`)
      if(luachon==true){
      try {
        let res = await axios.post("http://localhost:1993/api/delete-user", {
            id_user: s[k].id,
            value_role:role[k].value
        })
        if (res.data) {
            window.location.href = "/quantrivien"
        }
    } catch (error) {
        window.location.href = "/404"
    }
  }else{
    window.location.href = "/quantrivien"
  }
  })
  }
}
getTableUser();   



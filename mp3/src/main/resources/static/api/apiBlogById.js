const API_Blog = "http://localhost:1993/api/post";
const singerAll = document.querySelector(".list_singer");
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
//lấy API Người dùng
const anhdaidien = document.getElementById("anhdaidien")
const dangnhap = document.getElementById("dangnhap")
const dangky = document.getElementById("dangky")
const table_anhdaidien = document.getElementById("table-anhdaidien");
const admin = document.getElementById("admin")
const userComment = document.getElementById("user")
const getUser = async () => {
  let res = await axios.get("http://localhost:1993/tendangnhap");
  user = res.data;
  for (let j = 0; j < user.posts.length; j++) {
    if(user.posts[j]== id){
      admin.style.display = ``;
    }
  }
  if(user.roles == null){
    userComment.style.display = `none`;
  }
  // if(user.roles == null||user.roles.length==1){
  //   admin.style.display = `none`;
  // }
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
  //binh luận
btnbinhluan.addEventListener("click", async function (event) {
  try {
      let res = await axios.post(`http://localhost:1993/api/comment`, {
          user_id:user.id,
          post_id:id,
          text:binhluan.value
      })
      if (res.data) {
          alert("Đã Bình luận Thành Công")
          window.location.href = `/blog|${id}`
      }
  } catch (error) {
    alert("Bình luận Thật Bại")
  }
})
}
getUser();
//lấy id
var link = location.href;
var x = [];
for (let i = link.length - 1; i >= 0; i--) {
    if (link[i] == "C") {
        break;
    }
    x.push(link[i]);
}
const id = x.reverse().join("").replace('?', '');
console.log(id)
//lấy API Singer
const title = document.getElementById("title");
const tacgia = document.getElementById("tacgia");
const content = document.querySelector(".content")
const comments = document.getElementById("comments")
const binhluan = document.getElementById("binhluan");
const btnbinhluan = document.getElementById("btnbinhluan");
const getBlog = async () => {
   let res = await axios.get(`http://localhost:1993/api/blog/${id}`);
         var b = res.data;
title.innerHTML =  b.title;
tacgia.innerHTML = "tác giả: " + b.user.firstName + b.user.lastName;
content.innerHTML = b.content
var html = ``
for (let i = 0; i < b.comments.length; i++) {
 html += `<div style="width: 550px;" >
 <pre style="color: red;margin-bottom: 10px;"><img src="${b.users[i].avatar}" alt="" width="30px" height="30px" style="border-radius: 50%;">${b.users[i].firstName} ${b.users[i].lastName}</pre>                    
 <p style="color: pink;">${b.comments[i]}</p>
</div><br>`
}
comments.innerHTML = html;
}
getBlog();
const xoablog = document.querySelector(".xoablog")
xoablog.addEventListener("click", async function () {
  alert("Đã Xóa Bài Viết Thành Công")
  let res = await axios.delete(`http://localhost:1993/api/blog/delete/${id}`)
  window.location.href = "/blog"
})

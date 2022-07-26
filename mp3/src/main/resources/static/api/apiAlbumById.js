const API_AlbumById = "http://localhost:1993/api/category";
var link = location.href;
console.log(link)
let myPlayList = [];
let songs = [];
var singer;
var ca_sy;
const singerById = document.getElementById("singer-by-id")
const btnDel = document.getElementById("delete")
const nameEl = document.getElementById("nameEl")
const imgEl = document.getElementById("imgEl")
const editEl = document.getElementById("edit")
const list_song = document.getElementById("list-song")
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
const admin = document.getElementById("admin")
const getUser = async () => {
  let res = await axios.get("http://localhost:1993/tendangnhap");
  user = res.data;
  if(user.roles == null||user.roles.length==1){
    admin.style.display = `none`;
  }
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
//lấy id
var x = [];
for (let i = link.length - 1; i >= 0; i--) {
    if (link[i] == "C") {
        break;
    }
    x.push(link[i]);
}
const id = x.reverse().join("").replace('?', '');
console.log(id)

//Lấy dữ liệu API
const getListSinger = async () => {
    let res = await axios.get(`${API_AlbumById}/${id}`);
    singer = res.data;
    console.log(singer.name);
    editEl.innerHTML =
  `<a href="/album-edit|${id}" style="margin: 15px;" title="Chỉnh Sửa Album"><img src="/img/svg/editalbum.png" alt="" width="50px"></a>`
    console.log(editEl.innerHTML)
    nameEl.innerHTML = `Album : ${singer.name}`;
    imgEl.src = `${singer.avatar}`;
    list_song.innerHTML = `Danh sách bài hát trong Album ${singer.name}`;
}


const songAll = document.getElementById("song-table");

//lấy API Singer
const getListSong = async () => {
    let res = await axios.get(`${API_AlbumById}/song/${id}`);
    myPlayList = res.data;
    console.log(myPlayList);
    let html = "";
    for (let i = 0; i < myPlayList.length; i++) {
        let singer_name = []
        let singerId = []
        let s = myPlayList[i];
        let x = Array.from(myPlayList[i].singers);
        for (let j = 0; j < x.length; j++) {
            singer_name.push(x[j].name)
            singerId.push(x[j].id)
        }
    
            html += `
		</tr>
		<tr>
		  <td>
			<label class="users-table__checkbox">
			  <div class="categories-table-img">
			  <a href="/song|${s.id}"><img src="${s.avatar}" alt=""></a>
			  </div>
			</label>
		  </td>
		  <td>
			${s.name}
		  </td>
		  <td>
			<div class="pages-table-img">
			  ${singer_name.join(" & ")}
			</div>
		  </td>
		  <td><span class="badge-pending">+ ${s.view} view</span></td>
		</tr>
		`
        singer_name.splice(0, singer_name.length);
    }
    songAll.innerHTML = html
    // máy mp3
const musicContainer = document.getElementById("music-container");
const playBtn = document.getElementById("play");
const prevBtn = document.getElementById("prev");
const nextBtn = document.getElementById("next");

const audio = document.getElementById("audio");
const progress = document.getElementById("progress");
const progressContainer = document.getElementById("progress-container");
const title = document.getElementById("title");
const cover = document.getElementById("cover");

// máy mp3

    // Tiêu đề bài hát, tương ứng với các file mp3
    songs = myPlayList;
    
    // Lấy index bất kỳ trong mảng songs để hiện thị
    let songIndex = 0;
    
    // Load 1 bài hát theo index
    loadSong(songs[songIndex]);
    
    // Cập nhật thông tin bài hát
    function loadSong(song) {
        title.innerText = song.name;
        audio.src = song.mp3;
        cover.src = song.avatar;
    }
    
    // Play song
    function playSong() {
        musicContainer.classList.add("play");
        playBtn.querySelector("i.fas").classList.remove("fa-play");
        playBtn.querySelector("i.fas").classList.add("fa-pause");
    
        audio.play();
    }
    
    // Pause song
    function pauseSong() {
        musicContainer.classList.remove("play");
        playBtn.querySelector("i.fas").classList.add("fa-play");
        playBtn.querySelector("i.fas").classList.remove("fa-pause");
    
        audio.pause();
    }
    
    // Xử lý khi prev bài hát
    function prevSong() {
        // Xử lý khi prev bài hát
        songIndex--;
    
        // Nếu đang là bài hát đầu thì quay lại bài hát cuối
        if (songIndex < 0) {
            songIndex = songs.length - 1;
        }
    
        // Cập nhật thông tin của bài hát lên giao diện
        loadSong(songs[songIndex]);
    
        // Phát nhạc
        playSong();
    }
    
    // Next song
    function nextSong() {
        // Tăng index của songIndex lên 1
        songIndex++;
    
        // Nếu đang là bài hát cuối thì quay lại bài hát đầu
        if (songIndex > songs.length - 1) {
            songIndex = 0;
        }
    
        // Cập nhật thông tin của bài hát lên giao diện
        loadSong(songs[songIndex]);
    
        // Phát nhạc
        playSong();
}

// Update progress bar
function updateProgress(e) {
    const { duration, currentTime } = e.srcElement;
    const progressPercent = (currentTime / duration) * 100;
    progress.style.width = `${progressPercent}%`;
}

// Set progress bar
function setProgress(e) {
    const width = this.clientWidth;
    const clickX = e.offsetX;
    const duration = audio.duration;

    audio.currentTime = (clickX / width) * duration;
}

// Lắng nghe sự kiện
playBtn.addEventListener("click", () => {
    // Kiểm tra xem musicContainer có chứa class "play" hay không?
    const isPlaying = musicContainer.classList.contains("play");

    // Nếu có thì thực hiện pause
    // Nếu không thì thực hiện play
    if (isPlaying) {
        pauseSong();
    } else {
        playSong();
    }
});

// Lắng nghe sự kiện khi next và prev bài hát
prevBtn.addEventListener("click", prevSong);
nextBtn.addEventListener("click", nextSong);

// Time/song update
audio.addEventListener("timeupdate", updateProgress);

// Click on progress bar
progressContainer.addEventListener("click", setProgress);

// Lắng nghe sự kiện khi kết thúc bài hát
audio.addEventListener("ended", nextSong);

}
btnDel.addEventListener("click", async function () {
  alert("Đã Xóa Album")
    let res = await axios.delete(`${API_AlbumById}/delete/${id}`)
    window.location.href = "/album"
})
getListSong();
getListSinger();

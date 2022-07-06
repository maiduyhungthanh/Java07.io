const API_Song = "http://localhost:1993/api/song";
const songAll = document.getElementById("song-table");
const musicContainer = document.getElementById("music-container");
const playBtn = document.getElementById("play");
const prevBtn = document.getElementById("prev");
const nextBtn = document.getElementById("next");

const audio = document.getElementById("audio");
const progress = document.getElementById("progress");
const progressContainer = document.getElementById("progress-container");
const title = document.getElementById("title");
const cover = document.getElementById("cover");

let myPlayList = [];
let songs = [];
//lấy API Singer
const getListSong = async () => {
  let res = await axios.get(API_Song);
  myPlayList = res.data;
  console.log(myPlayList);
  let singer = []
  let singerId = []
  let html = "";
  for (let i = 0; i < myPlayList.length; i++) {
    songs = myPlayList[i];
    let s = myPlayList[i];
    let x = Array.from(myPlayList[i].singers);
    for (let j = 0; j < x.length; j++) {
      singer.push(x[j].name)
      singerId.push(x[j].id)
    }
    console.log(singer)
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
			  ${singer.join(" & ")}
			</div>
		  </td>
		  <td><svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="grey" class="bi bi-heart-fill" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
        </svg></td>
		</tr>
		`
    singer.splice(0, singer.length);
  }
  songAll.innerHTML = html

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

getListSong();

let list_album = [];
const albumAll = document.querySelector(".album");
console.log(albumAll.innerHTML);

//lấy API Album
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
const ngonngu = document.getElementById("ngonngu");
const table_ngonngu = document.getElementById("table-ngonngu");
ngonngu.addEventListener("click", function () {
  if (table_ngonngu.style.opacity == "0") {
    table_ngonngu.style.opacity = "1";
  } else {
    table_ngonngu.style.opacity = "0";
  }
})


const canhan = document.getElementById("canhan");
canhan.addEventListener("click",function(){
if(table_anhdaidien.style.opacity=="0"){
table_anhdaidien.style.opacity="1";
}else{
table_anhdaidien.style.opacity="0";
}
})



const API_Song = "http://localhost:1993/api/song";
const songAll = document.getElementById("song-table");
const edit = document.getElementById("edit");
const btnDel = document.getElementById("delete")

// lấy id
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
edit.innerHTML = `<a href="http://localhost:1993/song-edit|${ID}" >Chỉnh Sửa</a>`

let myPlayList = [];
//lấy API Singer
const getListSong = async () => {
	let res = await axios.get(`http://localhost:1993/api/song/${ID}`);
	myPlayList = res.data;
	console.log(myPlayList);
	let singer = []
	let singerId = []
	let html = "";
	let x = Array.from(myPlayList.singers);
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
			  <a href="/song|${myPlayList.id}"><img src="${myPlayList.avatar}" alt=""></a>
			  </div>
			</label>
		  </td>
		  <td>
			${myPlayList.name}
		  </td>
		  <td>
			<div class="pages-table-img">
			  ${singer.join(" & ")}
			</div>
		  </td>
		  <td><span class="badge-pending">1.000.000 view</span></td>
		</tr>
		`
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

// Tiêu đề bài hát, tương ứng với các file mp3
const songs = myPlayList.name;

// Lấy index bất kỳ trong mảng songs để hiện thị
let songIndex = 0;

// Load 1 bài hát theo index
loadSong(songs[songIndex]);

// Cập nhật thông tin bài hát
function loadSong(song) {
    title.innerText = myPlayList.name;
    audio.src = myPlayList.mp3;
    cover.src = myPlayList.avatar;
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

	let res = await axios.delete(`http://localhost:1993/api/song/delete/${ID}`)
	console.log(res)
	window.location.href = "/singer"
})
getListSong();



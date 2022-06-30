const API_SingerById = "http://localhost:1993/api/singer";
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


console.log(nameEl.innerHTML)
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
    let res = await axios.get(`${API_SingerById}/${id}`);
    singer = res.data;
    console.log(singer.name);
    editEl.innerHTML = `<a href="/singer-edit|${singer.id}">
    <p style="color: blueviolet;margin-right: -400px;font-size: 20px;">Chỉnh Sửa</p>
  </a>`
    console.log(editEl.innerHTML)
    nameEl.innerHTML = `Ca Sỹ : ${singer.name}`;
    imgEl.src = `${singer.avatar}`;
    list_song.innerHTML = `Danh sách bài hát của ${singer.name}`;
}


const API_Song = "http://localhost:1993/api/song";
const songAll = document.getElementById("song-table");

//lấy API Singer
const getListSong = async () => {
    let res = await axios.get(`${API_SingerById}/song/${id}`);
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
		  <td><span class="badge-pending">1.000.000 view</span></td>
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
console.log(`${API_SingerById}-delete/${id}`)
btnDel.addEventListener("click", async function () {

    let res = await axios.delete(`${API_SingerById}/delete/${id}`)
    console.log(res)
    window.location.href = "/singer"
})
getListSong();
getListSinger();

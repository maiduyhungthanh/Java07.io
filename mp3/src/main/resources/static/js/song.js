const API_URL = "http://localhost:1993/api/song";
let list_song = [];
let songs = [];
// Truy cập vào các thành phần
const musicContainer = document.getElementById("music-container");
const playBtn = document.getElementById("play");
const prevBtn = document.getElementById("prev");
const nextBtn = document.getElementById("next");

const audio = document.getElementById("audio");
const progress = document.getElementById("progress");
const progressContainer = document.getElementById("progress-container");
const title = document.getElementById("title");
const cover = document.getElementById("cover");
const singer = document.getElementById("singer_h4");

//APi lấy danh sách user kiêm tìm kiếm ListSong
const getListSongAPI = (keyword = "") => {
    let url = API_URL;
  console.log(url);
    return axios.get(url);
    
}
//..........................................
const getListSong = async (keyword ="") => {
    try {
        let res = await getListSongAPI(keyword)
        list_song = res.data;
// Tiêu đề bài hát, tương ứng với các file mp3
        for (let i = 0; i < list_song.length; i++) {
            songs.push(list_song[i].name)    
                
        }
     
// Lấy index bất kỳ trong mảng songs để hiện thị
let songIndex = Math.floor(Math.random() * list_song.length) ;

// Load 1 bài hát theo index
loadSong(songs[songIndex]);

// Cập nhật thông tin bài hát
function loadSong(song) {
    title.innerText = song;
    audio.src = `music/${song}.mp3`;
    cover.src = `image/${song}.jpg`;
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
singer.innerHTML = list_song[songIndex].singer;
console.log(singer.innerHTML)
    } catch (e) {
        console.log(e)
    }

}


getListSong();
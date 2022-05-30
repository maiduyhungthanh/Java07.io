
$(function(){
	var playItem = 0,
		title=$('.jp-interface .jp-title'),
		jPlayer=$("#jplayer"),
		myPlayList = [
			{name:"Đừng yêu nữa em mệt rồi",mp3:"music/dung_yeu_nua_em_met_roi.mp3",ogg:"music/dung_yeu_nua_em_met_roi.ogg"},
			{name:"Không sao mà em đây rồi",mp3:"music/dung_yeu_nua_em_met_roi.mp3",ogg:"audio/dung_yeu_nua_em_met_roi.ogg"},
			{name:"Hãy về đây bên anh",mp3:"music/dung_yeu_nua_em_met_roi.mp3",ogg:"music/dung_yeu_nua_em_met_roi.ogg"}
		],		
		jPlay=function(idx){
			if(typeof idx==typeof 0)
				jPlayer.jPlayer("setMedia",myPlayList[idx]).jPlayer('play')
			if(typeof idx==typeof '')
				jPlayer.jPlayer("setMedia",myPlayList[playItem=idx=='next'?(++playItem<myPlayList.length?playItem:0):(--playItem>=0?playItem:myPlayList.length-1)]).jPlayer('play')					
			title.text(myPlayList[playItem].name)
			Cufon.refresh()
		}

	jPlayer.jPlayer({
		ready: function() {
			jPlay(playItem)
		},
		ended:function(){
			jPlay('next')
		}
	})
	
	$(".jp-prev,.jp-next")
		.click( function() { 
			jPlay($(this).is('.jp-next')?'next':'prev')
			return false;
		})
	
});

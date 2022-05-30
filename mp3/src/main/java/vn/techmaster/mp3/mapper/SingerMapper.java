package vn.techmaster.mp3.mapper;

import vn.techmaster.mp3.dto.SingerDto;
import vn.techmaster.mp3.model.Singer;

public class SingerMapper{
    public static SingerDto toSingerDto(Singer singer){
        SingerDto singerDto = new SingerDto();
        singerDto.setName(singer.getName());
        singerDto.setAvatar(singer.getAvatar());
        singerDto.setId(singer.getId());
        return singerDto;
    }
}

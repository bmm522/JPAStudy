package com.callbus.community.domain;

import com.callbus.community.common.DateFormatter;
import com.callbus.community.domain.util.AccountType;
import com.callbus.community.service.dto.ServiceRequestDto;
import com.callbus.community.service.dto.response.ServiceDeleteBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceGetBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceSaveBoardResponseDto;
import com.callbus.community.service.dto.response.ServiceUpdateBoardResponseDto;
import com.callbus.community.domain.util.BaseTimeEntity;
import com.callbus.community.domain.util.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @ColumnDefault(value = "0")
    @Column(nullable = false)
    private Integer hit = 0;




    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Like> likes;

    @OneToMany(mappedBy = "board")
    private List<Reply> reply;

//    @Builder
//    public Board(Long boardId,String title, String content, Status status, LocalDateTime createDate){
//        this.boardId = boardId;
//        this.title = title;
//        this.content = content;
//        this.createDate = createDate;
//        this.status = status;
//    }

    @Builder // getList Test 용 생성자
    public Board(Long boardId,String title, String content, Status status, LocalDateTime createDate,List<Like> likes){
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.status = status;
        this.likes = likes;
    }


    @Builder
    public Board(Long boardId, String title, String content, Integer hit,Status status) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.deleteDate = null;
        this.status = status;
    }


    public ServiceSaveBoardResponseDto toSaveDto(){
        return ServiceSaveBoardResponseDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .nickname(this.getMember().getNickname())
                .memberId(this.getMember().getMemberId())
                .createDate(createDate)
                .build();

    }

    public ServiceGetBoardResponseDto toGetDto(Long targetMemberId, AccountType accountType){
        return ServiceGetBoardResponseDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .createDate(DateFormatter.getInstance().formatDate(createDate))
                .updateDate((updateDate == null) ? "N" : DateFormatter.getInstance().formatDate(updateDate))
                .writer(member.getNickname()+"("+ getAccountType(member.getAccountType())+")")
                .memberId(member.getMemberId())
                .hit(hit)
                .likeCount(likes.size())
                .targetMemberModificationPermission((member.getMemberId().equals(targetMemberId))?"Y":"N")
                .targetMemberIsLike((accountType.equals(AccountType.externalUser))?"EX":(checkTargetMemberIsLike(likes,targetMemberId))?"Y":"N")
                .build();
    }

    private boolean checkTargetMemberIsLike(List<Like> likes,Long targetUserId) {
        for(Like like : likes){
            if(like.getMember().getMemberId().equals(targetUserId)){
                return true;
            }
        }
        return false;
    }

    public ServiceUpdateBoardResponseDto toUpdateDto(){
        return ServiceUpdateBoardResponseDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .nickname(member.getNickname())
                .memberId(member.getMemberId())
                .createDate(createDate)
                .updateDate(updateDate)
                .build();

    }

    public ServiceDeleteBoardResponseDto toDeleteDto() {
        return ServiceDeleteBoardResponseDto.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .nickname(member.getNickname())
                .memberId(member.getMemberId())
                .createDate(createDate)
                .deleteDate(deleteDate)
                .build();
    }

    private String getAccountType(AccountType accountType){
        switch (accountType){
            case Realtor:return "공인중개사";
            case Lessor:return "임대인";
            case Lessee:return "임차인";
            default: return null;
        }
    }

    public Board update(String title, String content, LocalDateTime updateDate){
        this.title = title;
        this.content = content;
        this.updateDate = updateDate;
        return this;
    }

    public Board updateHit(Integer hit) {
        this.hit = hit+1;
        return this;
    }

    public Board delete(LocalDateTime deleteDate, Status status) {
        this.deleteDate = deleteDate;
        this.status = status;
        return this;
    }

    public void addMember(Member member){
        this.member = member;
        member.addBoard(this);
    }

    public void addLike(Like like){
        this.likes.add(like);
        if(like.getBoard() != this){
            like.addBoard(this);
        }
    }

    public boolean checkWriter(ServiceRequestDto dto){
        return member.getMemberId().equals(dto.getMemberId());
    }






}

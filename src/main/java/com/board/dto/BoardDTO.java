package com.board.dto;

import com.board.entity.BoardEntity;
import com.board.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {

    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private Integer boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    private List<MultipartFile> boardFile; // 여러 개의 파일이 List에 담겨서 DTO로 넘어옴
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0), boolean으로 해도 되지만 엔티티에서 손이 많이 가게 되므로 int형 사용

    private List<BoardFileDTO> fileList;

    public BoardDTO(Long id, String boardWriter, String boardTitle, Integer boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        // Entity에 담긴 값들을 DTO 객체로 옮겨 담음
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(0);
        } else {
            boardDTO.setFileAttached(1);
            List<BoardFileDTO> boardFileDTOList = new ArrayList<>();
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                boardFileDTOList.add(BoardFileDTO.toBoardFileDTO(boardFileEntity));
            }
            boardDTO.setFileList(boardFileDTOList);
        }

        return boardDTO;
    }
}

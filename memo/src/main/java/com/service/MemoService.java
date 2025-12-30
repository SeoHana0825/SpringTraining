package com.service;

import com.dto.*;
import com.entity.Memo;
import com.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional
    public MemoCreateResponse save(MemoCreateRequest request) {
        Memo memo = new Memo (request.getText());
        Memo savedMemo = memoRepository.save(memo);
        return new MemoCreateResponse(
                savedMemo.getId(),
                savedMemo.getText(),
                savedMemo.getCreatedAt(),
                savedMemo.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<MemoGetResponse> findAll() {
        List<Memo> memos = memoRepository.findAll();
        List<MemoGetResponse> dtos = new ArrayList<>();
        for (Memo memo : memos) {
            MemoGetResponse dto = new MemoGetResponse(
                    memo.getId(),
                    memo.getText(),
                    memo.getCreatedAt(),
                    memo.getModifiedAt()
            );
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public MemoGetResponse findOne(Long memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 메모입니다")
        );

        return new MemoGetResponse(
                memo.getId(),
                memo.getText(),
                memo.getCreatedAt(),
                memo.getModifiedAt()
        );
    }

    @Transactional
    public MemoUpdateResponse update(Long memoId, MemoUpdateRequest request) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(
                () -> new IllegalStateException ("존재하지 않는 메모입니다")
        );
        memo.update(request.getText());
        return new MemoUpdateResponse(
                memo.getId(),
                memo.getText(),
                memo.getCreatedAt(),
                memo.getModifiedAt()
        );
    }

    @Transactional
    public void delete(Long memoId) {
        boolean existence = memoRepository.existsById(memoId);

        if(!existence) {
            throw new IllegalStateException("존재하지 않는 메모입니다");
        }

        memoRepository.deleteById(memoId);
    }
}

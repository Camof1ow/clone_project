package com.project.cloneproject.service;

import com.project.cloneproject.controller.request.AddFriendRequestDto;
import com.project.cloneproject.controller.request.FriendSearchRequestDto;
import com.project.cloneproject.controller.response.FriendResponseDto;
import com.project.cloneproject.controller.response.ResponseDto;
import com.project.cloneproject.domain.Friend;
import com.project.cloneproject.domain.Member;
import com.project.cloneproject.repository.FriendRepository;
import com.project.cloneproject.repository.MemberRepository;
import com.project.cloneproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public ResponseEntity<?> addFriend(AddFriendRequestDto requestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();


        // 내가 저장할 친구
        Member fromMember = memberRepository.findById(requestDto.getMember_id()).get();

        // friend의 id로 검색
//        Optional<Friend> friend = friendRepository.findByMemberAndId(member, fromMember.getId());
        List<Friend> friends = friendRepository.findAllByMember(member);
        for (Friend friend : friends) {
            if (friend.getFromMember().equals(fromMember)) {
                return new ResponseEntity<>(ResponseDto.fail("FRIEND_IS_ALREADY_BE_REGISTED", "친구가 이미 등록되어 있습니다"), HttpStatus.BAD_REQUEST);
            }
        }

        if (fromMember.equals(member)) {
            return new ResponseEntity<>(ResponseDto.fail("CAN_NOT_REGISTER", "자신은 등록할 수 없습니다"), HttpStatus.BAD_REQUEST);
        }

        Friend savedFriend = Friend.builder()
                .member(member)
                .fromMember(fromMember)
                .build();

        friendRepository.save(savedFriend);

        List<Member> addFriend = new ArrayList<>();
        addFriend.add(fromMember);

        return new ResponseEntity<>(ResponseDto.success(addFriend), HttpStatus.OK);

    }

    /*public ResponseDto<?> searchFriend(String, HttpServletRequest request) {

        // 멤버를 가지고 오기
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (post.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
    }*/

    public FriendResponseDto getFriends(UserDetailsImpl userDetails) {

        Member member = userDetails.getMember();

        List<Friend> allByMember = friendRepository.findAllByMember(member);
        List<Member> friends = new ArrayList<>();
        for (Friend friend : allByMember) {
            friends.add(friend.getFromMember());
        }

        FriendResponseDto friendResponseDto = FriendResponseDto.builder()
                .friends(friends)
                .build();

        return friendResponseDto;
    }

    public ResponseDto<?> searchFriend(FriendSearchRequestDto requestDto) {
        return ResponseDto.success(memberRepository.findAllByNicknameContaining(requestDto.getNickname()));
    }
}

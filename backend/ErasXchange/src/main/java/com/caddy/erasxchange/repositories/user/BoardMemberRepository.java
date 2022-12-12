package com.caddy.erasxchange.repositories.user;

import com.caddy.erasxchange.models.users.BoardMember;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMemberRepository extends UserRepository<BoardMember> {
}

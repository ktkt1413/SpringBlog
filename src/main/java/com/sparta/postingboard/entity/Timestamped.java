package com.sparta.postingboard.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // timestamped를 상속할 경우 timestamped를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // 자동으로 시간을 넣어줌. 아래의 클래스에 Auditing기술을 포함시켜줌

public abstract class Timestamped {  //다른 엔티티에 상속하기 위한 클래스

    @CreatedDate
    @Column(updatable = false)  // 생성된 시간은 계속 남겨두는가?
    @Temporal(TemporalType.TIMESTAMP) //TIMESTAMP : date + time
    private LocalDateTime createAt;

    @LastModifiedDate  // 조회한 엔티티 값을 변경할때 변경된 시간을 적용
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}

package kr.kro.jayden_bin.greenmate.entity.community

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import kr.kro.jayden_bin.greenmate.entity.BaseTimeEntity

@Entity
@Table(
    name = "community_images",
)
@SequenceGenerator(name = "COMMUNITY_IMAGE_SEQ_GENERATOR", sequenceName = "COMMUNITY_IMAGE_SEQ", initialValue = 1, allocationSize = 1)
class CommunityImage(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNITY_IMAGE_SEQ_GENERATOR")
    @Column(updatable = false, nullable = false)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", updatable = false)
    val community: Community,
    @Column(length = 50, nullable = false)
    val fileName: String,
) : BaseTimeEntity()

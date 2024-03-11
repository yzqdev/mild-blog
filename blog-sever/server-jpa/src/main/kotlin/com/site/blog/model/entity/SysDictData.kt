package com.site.blog.model.entity


import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDateTime

/**
 * @author yanni
 * @date time 2022/6/17 12:33
 * @modified By:
 */
@Entity
data class SysDictData (

    var typeId: String? = null,
    var value: String? = null,
    var code: String? = null,
    var sort: Int? = null,
    var remark: String? = null,
    var status: Boolean? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var createTime: LocalDateTime? = null,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var updateTime: LocalDateTime? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SysDictData

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , typeId = $typeId , value = $value , code = $code , sort = $sort , remark = $remark , status = $status , createTime = $createTime , updateTime = $updateTime )"
    }
}
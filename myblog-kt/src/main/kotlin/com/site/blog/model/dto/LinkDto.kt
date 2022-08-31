package com.site.blog.model.dto

import com.site.blog.model.entity.Link

data class LinkDto (
    var favoriteLinks: List<Link>? = null,
    var recommendLinks: List<Link>? = null,
    var personalLinks: List<Link>? = null
    )
package com.github.kommerce.users.repo

import com.github.kommerce.common.repo.Repository
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId

interface UsersRepository : Repository<UserId, User>
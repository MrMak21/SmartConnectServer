package gr.makris.smartConnect.response.users

import gr.makris.smartConnect.data.user.User
import gr.makris.smartConnect.response.base.SmartConnectResponse

class GetUsersResponse(
    val usersList: List<User>
): SmartConnectResponse() {
}
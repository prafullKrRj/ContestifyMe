package com.prafull.contestifyme.app.ai.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.contestifyme.app.ai.chatScreen.ChatMessage
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Database(entities = [ChatEntity::class], version = 1)
@TypeConverters(ChatMessageTypeConverter::class)
abstract class AIDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}

@Dao
interface ChatDao {
    @Query("SELECT * FROM chats WHERE chatId = :chatId")
    suspend fun getChatById(chatId: String): ChatEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Query("SELECT * FROM chats order by lastModified desc")
    fun getAllChats(): Flow<List<ChatEntity>>

    @Query("DELETE FROM chats")
    suspend fun deleteAll()
}

class ChatMessageTypeConverter {
    @TypeConverter
    fun fromChatMessageList(messages: List<ChatMessage>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ChatMessage>>() {}.type
        return gson.toJson(messages, type)
    }

    @TypeConverter
    fun toChatMessageList(messagesString: String): List<ChatMessage> {
        val gson = Gson()
        val type = object : TypeToken<List<ChatMessage>>() {}.type
        return gson.fromJson(messagesString, type)
    }
}

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey val chatId: String = UUID.randomUUID().toString(),
    val chatHeading: String,
    val lastModified: Long,
    @TypeConverters(ChatMessageTypeConverter::class) val messages: List<ChatMessage>
)
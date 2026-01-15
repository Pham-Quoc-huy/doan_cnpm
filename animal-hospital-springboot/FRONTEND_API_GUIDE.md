# Hướng Dẫn Triển Khai Chatbot AI cho Frontend

## 📋 Mục Lục

1. [Tổng Quan](#tổng-quan)
2. [Các API Endpoints](#các-api-endpoints)
3. [Cách Triển Khai ở Frontend](#cách-triển-khai-ở-frontend)
4. [Ví Dụ Code Hoàn Chỉnh](#ví-dụ-code-hoàn-chỉnh)
5. [Best Practices](#best-practices)

---

## Tổng Quan

### Hai Chế Độ Sử Dụng

#### 1. **Authenticated User** (User đã đăng nhập)

- ✅ Lưu lịch sử chat vào database
- ✅ Quản lý sessions (tạo, xóa, xem lại)
- ✅ Lưu lịch sử lâu dài
- ✅ Có thể chat với thông tin thú cưng

#### 2. **Anonymous User** (User chưa đăng nhập)

- ✅ Chat với AI ngay lập tức
- ❌ Không lưu lịch sử vào database
- ❌ Không quản lý sessions
- ⚠️ Lịch sử chỉ tồn tại trong session hiện tại (mất khi refresh)

### Base URL

```
http://localhost:8080/api/chat
```

---

## Các API Endpoints

### 🔐 Endpoints Yêu Cầu Authentication

#### 1. Tạo Session Mới

```http
POST /api/chat/sessions
Authorization: Bearer {token}
```

**Response:**

```json
{
  "id": 1,
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Cuộc trò chuyện mới",
  "createdDate": "2024-01-15T10:30:00Z",
  "lastMessageDate": "2024-01-15T10:30:00Z",
  "messageCount": 0,
  "userId": 1
}
```

#### 2. Gửi Tin Nhắn (Authenticated)

```http
POST /api/chat/messages
Authorization: Bearer {token}
Content-Type: application/json

{
  "message": "Chó của tôi bị nôn mửa, phải làm sao?",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "petId": null
}
```

**Response:**

```json
{
  "response": "Xin chào! Tôi là bác sĩ thú y AI...",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "messageId": 2,
  "createdDate": "2024-01-15T10:31:00Z"
}
```

#### 3. Lấy Lịch Sử Chat

```http
GET /api/chat/sessions/{sessionId}
Authorization: Bearer {token}
```

#### 4. Lấy Danh Sách Sessions

```http
GET /api/chat/sessions
Authorization: Bearer {token}
```

#### 5. Xóa Session

```http
DELETE /api/chat/sessions/{sessionId}
Authorization: Bearer {token}
```

---

### 🌐 Endpoint Public (Không Cần Authentication)

#### Gửi Tin Nhắn (Anonymous)

```http
POST /api/chat/public/messages
Content-Type: application/json

{
  "message": "Chó của tôi bị nôn mửa, phải làm sao?",
  "sessionId": null,
  "petId": null
}
```

**Response:**

```json
{
  "response": "Xin chào! Tôi là bác sĩ thú y AI...",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "messageId": null,
  "createdDate": "2024-01-15T10:31:00Z"
}
```

**Lưu Ý:**

- `messageId` sẽ là `null` vì không lưu vào database
- `sessionId` sẽ được tạo tự động (UUID) nếu không có
- Không có lịch sử chat từ các lần chat trước

---

## Cách Triển Khai ở Frontend

### Bước 1: Tạo Service Class

Tạo file `chatService.ts` hoặc `chatService.js`:

```typescript
// chatService.ts

const BASE_URL = "http://localhost:8080/api/chat";

export interface ChatRequestDTO {
  message: string;
  sessionId?: string;
  petId?: number;
}

export interface ChatResponseDTO {
  response: string;
  sessionId: string;
  messageId: number | null;
  createdDate: string;
  confidence?: number;
  recommendation?: string;
}

export interface ChatSessionDTO {
  id: number;
  sessionId: string;
  title: string;
  createdDate: string;
  lastMessageDate: string;
  messageCount: number;
  userId: number;
}

export interface ChatMessageDTO {
  id: number;
  userId: number;
  petId?: number;
  sessionId: string;
  message?: string;
  response?: string;
  messageType: "USER" | "AI";
  createdDate: string;
}

export interface ChatHistoryDTO {
  sessionId: string;
  messages: ChatMessageDTO[];
  createdDate: string;
  lastMessageDate: string;
  messageCount: number;
}

class ChatService {
  private token: string | null = null;

  setToken(token: string | null) {
    this.token = token;
  }

  // ========== Authenticated Endpoints ==========

  async createSession(): Promise<ChatSessionDTO> {
    if (!this.token) throw new Error("Token required");

    const response = await fetch(`${BASE_URL}/sessions`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${this.token}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to create session: ${response.statusText}`);
    }

    return await response.json();
  }

  async sendMessage(
    message: string,
    sessionId?: string,
    petId?: number
  ): Promise<ChatResponseDTO> {
    if (!this.token) throw new Error("Token required");

    const response = await fetch(`${BASE_URL}/messages`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${this.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        message,
        sessionId,
        petId,
      }),
    });

    if (!response.ok) {
      throw new Error(`Failed to send message: ${response.statusText}`);
    }

    return await response.json();
  }

  async getChatHistory(sessionId: string): Promise<ChatHistoryDTO> {
    if (!this.token) throw new Error("Token required");

    const response = await fetch(`${BASE_URL}/sessions/${sessionId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${this.token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to get chat history: ${response.statusText}`);
    }

    return await response.json();
  }

  async getSessions(): Promise<ChatSessionDTO[]> {
    if (!this.token) throw new Error("Token required");

    const response = await fetch(`${BASE_URL}/sessions`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${this.token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to get sessions: ${response.statusText}`);
    }

    return await response.json();
  }

  async deleteSession(sessionId: string): Promise<void> {
    if (!this.token) throw new Error("Token required");

    const response = await fetch(`${BASE_URL}/sessions/${sessionId}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${this.token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to delete session: ${response.statusText}`);
    }
  }

  // ========== Public Endpoint (Anonymous) ==========

  async sendMessageAnonymous(
    message: string,
    sessionId?: string,
    petId?: number
  ): Promise<ChatResponseDTO> {
    const response = await fetch(`${BASE_URL}/public/messages`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        message,
        sessionId,
        petId,
      }),
    });

    if (!response.ok) {
      throw new Error(`Failed to send message: ${response.statusText}`);
    }

    return await response.json();
  }
}

export const chatService = new ChatService();
```

---

### Bước 2: Tạo State Management (React Example)

Tạo file `useChat.ts` (React Hook):

```typescript
// useChat.ts
import { useState, useCallback, useRef } from "react";
import { chatService, ChatMessageDTO, ChatResponseDTO } from "./chatService";

interface UseChatOptions {
  isAuthenticated: boolean;
  token?: string | null;
  petId?: number;
}

export function useChat({ isAuthenticated, token, petId }: UseChatOptions) {
  const [messages, setMessages] = useState<ChatMessageDTO[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [currentSessionId, setCurrentSessionId] = useState<string | null>(null);

  // Set token khi user login
  if (token) {
    chatService.setToken(token);
  }

  const sendMessage = useCallback(
    async (message: string) => {
      if (!message.trim()) return;

      // Thêm tin nhắn user vào UI ngay lập tức
      const userMessage: ChatMessageDTO = {
        id: Date.now(), // Temporary ID
        userId: 0,
        sessionId: currentSessionId || "",
        message,
        messageType: "USER",
        createdDate: new Date().toISOString(),
      };

      setMessages((prev) => [...prev, userMessage]);
      setIsLoading(true);
      setError(null);

      try {
        let response: ChatResponseDTO;

        if (isAuthenticated) {
          // Gửi tin nhắn với authentication
          response = await chatService.sendMessage(
            message,
            currentSessionId || undefined,
            petId
          );
        } else {
          // Gửi tin nhắn anonymous
          response = await chatService.sendMessageAnonymous(
            message,
            currentSessionId || undefined,
            petId
          );
        }

        // Cập nhật sessionId nếu có
        if (response.sessionId) {
          setCurrentSessionId(response.sessionId);
        }

        // Thêm tin nhắn AI vào UI
        const aiMessage: ChatMessageDTO = {
          id: response.messageId || Date.now(),
          userId: 0,
          sessionId: response.sessionId,
          response: response.response,
          messageType: "AI",
          createdDate: response.createdDate,
        };

        setMessages((prev) => [...prev, aiMessage]);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Failed to send message");
        // Xóa tin nhắn user nếu lỗi
        setMessages((prev) => prev.filter((msg) => msg.id !== userMessage.id));
      } finally {
        setIsLoading(false);
      }
    },
    [isAuthenticated, currentSessionId, petId]
  );

  const loadChatHistory = useCallback(
    async (sessionId: string) => {
      if (!isAuthenticated || !token) {
        throw new Error("Authentication required to load chat history");
      }

      setIsLoading(true);
      setError(null);

      try {
        const history = await chatService.getChatHistory(sessionId);
        setMessages(history.messages);
        setCurrentSessionId(sessionId);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "Failed to load chat history"
        );
      } finally {
        setIsLoading(false);
      }
    },
    [isAuthenticated, token]
  );

  const startNewChat = useCallback(() => {
    setMessages([]);
    setCurrentSessionId(null);
    setError(null);
  }, []);

  return {
    messages,
    isLoading,
    error,
    currentSessionId,
    sendMessage,
    loadChatHistory,
    startNewChat,
  };
}
```

---

### Bước 3: Tạo UI Component (React Example)

Tạo file `ChatComponent.tsx`:

```tsx
// ChatComponent.tsx
import React, { useState, useRef, useEffect } from "react";
import { useChat } from "./useChat";

interface ChatComponentProps {
  isAuthenticated: boolean;
  token?: string | null;
  petId?: number;
}

export const ChatComponent: React.FC<ChatComponentProps> = ({
  isAuthenticated,
  token,
  petId,
}) => {
  const [inputMessage, setInputMessage] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const { messages, isLoading, error, sendMessage, startNewChat } = useChat({
    isAuthenticated,
    token,
    petId,
  });

  // Auto scroll to bottom khi có tin nhắn mới
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputMessage.trim() || isLoading) return;

    const message = inputMessage;
    setInputMessage("");
    await sendMessage(message);
  };

  return (
    <div className="chat-container">
      {/* Header */}
      <div className="chat-header">
        <h2>Chat với Bác Sĩ Thú Y AI</h2>
        {!isAuthenticated && (
          <div className="warning-banner">
            ⚠️ Bạn đang chat ở chế độ khách. Đăng nhập để lưu lịch sử chat.
          </div>
        )}
        <button onClick={startNewChat}>Chat Mới</button>
      </div>

      {/* Messages */}
      <div className="messages-container">
        {messages.length === 0 && (
          <div className="empty-state">
            <p>
              Xin chào! Tôi là bác sĩ thú y AI. Hãy hỏi tôi về thú cưng của bạn.
            </p>
          </div>
        )}

        {messages.map((msg) => (
          <div
            key={msg.id}
            className={`message ${
              msg.messageType === "USER" ? "user-message" : "ai-message"
            }`}
          >
            <div className="message-content">
              {msg.messageType === "USER" ? (
                <p>{msg.message}</p>
              ) : (
                <div>
                  <p>{msg.response}</p>
                  {isLoading && msg === messages[messages.length - 1] && (
                    <span className="typing-indicator">...</span>
                  )}
                </div>
              )}
            </div>
            <div className="message-time">
              {new Date(msg.createdDate).toLocaleTimeString()}
            </div>
          </div>
        ))}

        {isLoading && messages.length > 0 && (
          <div className="message ai-message">
            <div className="message-content">
              <div className="typing-indicator">AI đang suy nghĩ...</div>
            </div>
          </div>
        )}

        <div ref={messagesEndRef} />
      </div>

      {/* Error Message */}
      {error && <div className="error-message">❌ {error}</div>}

      {/* Input Form */}
      <form onSubmit={handleSubmit} className="chat-input-form">
        <input
          type="text"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
          placeholder="Nhập câu hỏi của bạn..."
          disabled={isLoading}
          maxLength={2000}
        />
        <button type="submit" disabled={isLoading || !inputMessage.trim()}>
          {isLoading ? "Đang gửi..." : "Gửi"}
        </button>
      </form>
    </div>
  );
};
```

---

### Bước 4: Sử Dụng Component

```tsx
// App.tsx hoặc trang chat của bạn
import { ChatComponent } from "./ChatComponent";
import { useSelector } from "react-redux"; // hoặc context của bạn

function ChatPage() {
  const isAuthenticated = useSelector((state) => state.auth.isAuthenticated);
  const token = useSelector((state) => state.auth.token);
  const selectedPetId = useSelector((state) => state.pets.selectedPetId);

  return (
    <div>
      <ChatComponent
        isAuthenticated={isAuthenticated}
        token={token}
        petId={selectedPetId}
      />
    </div>
  );
}
```

---

## Ví Dụ Code Hoàn Chỉnh

### Flow 1: User Chưa Đăng Nhập (Anonymous)

```typescript
// 1. User mở trang chat
const chatService = new ChatService();

// 2. User gửi tin nhắn đầu tiên
const response1 = await chatService.sendMessageAnonymous(
  "Chó của tôi bị nôn mửa"
);
// Response: { sessionId: "uuid-1", response: "...", messageId: null }

// 3. Lưu sessionId vào state/localStorage để tiếp tục chat
let currentSessionId = response1.sessionId;

// 4. User gửi tin nhắn tiếp theo
const response2 = await chatService.sendMessageAnonymous(
  "Tôi nên làm gì?",
  currentSessionId // Dùng sessionId từ lần trước
);

// ⚠️ Lưu ý: Nếu user refresh trang, sessionId sẽ mất
// → Có thể lưu vào localStorage nhưng lịch sử vẫn không có
```

### Flow 2: User Đã Đăng Nhập

```typescript
// 1. Set token
chatService.setToken(userToken);

// 2. Tạo session mới (optional - có thể bỏ qua nếu dùng sendMessage)
const session = await chatService.createSession();
// Response: { sessionId: "uuid-1", title: "Cuộc trò chuyện mới", ... }

// 3. Gửi tin nhắn
const response = await chatService.sendMessage(
  "Chó của tôi bị nôn mửa",
  session.sessionId
);
// Response: { sessionId: "uuid-1", response: "...", messageId: 1 }

// 4. Lấy lịch sử chat sau này
const history = await chatService.getChatHistory(session.sessionId);
// Response: { messages: [...], sessionId: "uuid-1", ... }

// 5. Lấy danh sách tất cả sessions
const sessions = await chatService.getSessions();
// Response: [{ sessionId: "...", title: "...", ... }, ...]
```

---

## Best Practices

### 1. **Xử Lý Authentication**

```typescript
// Kiểm tra authentication trước khi gọi API
const sendMessage = async (message: string) => {
  const isAuthenticated = !!token;

  if (isAuthenticated) {
    return await chatService.sendMessage(message, sessionId, petId);
  } else {
    return await chatService.sendMessageAnonymous(message, sessionId, petId);
  }
};
```

### 2. **Lưu SessionId cho Anonymous User**

```typescript
// Lưu vào localStorage để giữ sessionId khi refresh
const saveSessionId = (sessionId: string) => {
  localStorage.setItem("anonymous_session_id", sessionId);
};

const getSessionId = (): string | null => {
  return localStorage.getItem("anonymous_session_id");
};
```

### 3. **Xử Lý Lỗi**

```typescript
try {
  const response = await chatService.sendMessage(message);
} catch (error) {
  if (error.message.includes("401")) {
    // Token hết hạn → Redirect đến login
    window.location.href = "/login";
  } else if (error.message.includes("400")) {
    // Bad request → Hiển thị lỗi cho user
    showError("Vui lòng kiểm tra lại tin nhắn của bạn");
  } else {
    // Lỗi khác → Retry hoặc hiển thị thông báo
    showError("Đã có lỗi xảy ra. Vui lòng thử lại.");
  }
}
```

### 4. **Loading State**

```typescript
// Hiển thị loading indicator khi đang gửi tin nhắn
const [isLoading, setIsLoading] = useState(false);

const sendMessage = async (message: string) => {
  setIsLoading(true);
  try {
    const response = await chatService.sendMessage(message);
    // ...
  } finally {
    setIsLoading(false);
  }
};
```

### 5. **Optimistic UI Update**

```typescript
// Hiển thị tin nhắn user ngay lập tức, không đợi response
const sendMessage = async (message: string) => {
  // Thêm tin nhắn user vào UI ngay
  addMessageToUI({ type: "USER", message });

  // Gửi request
  const response = await chatService.sendMessage(message);

  // Thêm response AI vào UI
  addMessageToUI({ type: "AI", message: response.response });
};
```

### 6. **Debounce cho Input**

```typescript
// Tránh gửi quá nhiều request
import { debounce } from "lodash";

const debouncedSendMessage = debounce(
  (message: string) => chatService.sendMessage(message),
  500
);
```

### 7. **Retry Logic**

```typescript
const sendMessageWithRetry = async (
  message: string,
  maxRetries = 3
): Promise<ChatResponseDTO> => {
  for (let i = 0; i < maxRetries; i++) {
    try {
      return await chatService.sendMessage(message);
    } catch (error) {
      if (i === maxRetries - 1) throw error;
      await new Promise((resolve) => setTimeout(resolve, 1000 * (i + 1)));
    }
  }
  throw new Error("Max retries exceeded");
};
```

---

## TypeScript Interfaces Đầy Đủ

```typescript
// Request DTOs
interface ChatRequestDTO {
  message: string; // Bắt buộc, tối đa 2000 ký tự
  sessionId?: string; // Optional
  petId?: number; // Optional
}

// Response DTOs
interface ChatResponseDTO {
  response: string;
  sessionId: string;
  messageId: number | null; // null cho anonymous
  createdDate: string;
  confidence?: number;
  recommendation?: string;
}

interface ChatSessionDTO {
  id: number;
  sessionId: string;
  title: string;
  createdDate: string;
  lastMessageDate: string;
  messageCount: number;
  userId: number;
}

interface ChatMessageDTO {
  id: number;
  userId: number;
  petId?: number;
  sessionId: string;
  message?: string;
  response?: string;
  messageType: "USER" | "AI";
  createdDate: string;
}

interface ChatHistoryDTO {
  sessionId: string;
  messages: ChatMessageDTO[];
  createdDate: string;
  lastMessageDate: string;
  messageCount: number;
}
```

---

## Tóm Tắt Checklist cho Frontend

### ✅ Cần Làm:

1. **Tạo ChatService**

   - [ ] Implement authenticated endpoints
   - [ ] Implement anonymous endpoint
   - [ ] Xử lý token management

2. **Tạo State Management**

   - [ ] Quản lý messages state
   - [ ] Quản lý sessionId
   - [ ] Quản lý loading state
   - [ ] Quản lý error state

3. **Tạo UI Components**

   - [ ] Chat container
   - [ ] Message list
   - [ ] Input form
   - [ ] Loading indicator
   - [ ] Error display

4. **Xử Lý Authentication**

   - [ ] Kiểm tra user đã login chưa
   - [ ] Chọn endpoint phù hợp (authenticated vs anonymous)
   - [ ] Redirect đến login nếu token hết hạn

5. **UX Improvements**

   - [ ] Auto scroll to bottom
   - [ ] Optimistic UI updates
   - [ ] Loading states
   - [ ] Error handling
   - [ ] Retry logic

6. **Optional Features**
   - [ ] Lưu sessionId vào localStorage (anonymous)
   - [ ] Hiển thị danh sách sessions (authenticated)
   - [ ] Chọn thú cưng trước khi chat
   - [ ] Markdown rendering cho AI response

---

## Kết Luận

Frontend cần:

1. **6 API Endpoints:**

   - `POST /api/chat/sessions` - Tạo session (authenticated)
   - `POST /api/chat/messages` - Gửi tin nhắn (authenticated)
   - `POST /api/chat/public/messages` - Gửi tin nhắn (anonymous) ⭐
   - `GET /api/chat/sessions/{sessionId}` - Lấy lịch sử (authenticated)
   - `GET /api/chat/sessions` - Danh sách sessions (authenticated)
   - `DELETE /api/chat/sessions/{sessionId}` - Xóa session (authenticated)

2. **Logic Quan Trọng:**

   - Kiểm tra authentication để chọn endpoint phù hợp
   - Quản lý sessionId để tiếp tục cuộc trò chuyện
   - Xử lý lỗi và loading states
   - Optimistic UI updates

3. **User Experience:**
   - Cho phép anonymous user chat ngay
   - Khuyến khích đăng nhập để lưu lịch sử
   - Hiển thị rõ ràng chế độ đang sử dụng

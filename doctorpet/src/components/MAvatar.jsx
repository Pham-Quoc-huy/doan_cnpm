import React from "react";
import "./css/MAvatar.css";
import "remixicon/fonts/remixicon.css";

/**
 * Helper function: Lấy ảnh pet dựa trên privacy
 * @param {string} imageUrl - URL ảnh của pet
 * @param {boolean} privacy - Trạng thái privacy (true = công khai, false = riêng tư)
 * @returns {string|null} - URL ảnh hoặc null nếu không có ảnh
 */
export const getPetImageByPrivacy = (imageUrl, privacy = true) => {
  // Kiểm tra imageUrl có phải là string và không rỗng
  if (!imageUrl || typeof imageUrl !== 'string') {
    return null;
  }
  
  const trimmedUrl = imageUrl.trim();
  if (trimmedUrl === "" || trimmedUrl === "null" || trimmedUrl === "undefined") {
    return null;
  }
  
  // Kiểm tra nếu là base64 string
  if (trimmedUrl.startsWith("data:image")) {
    return trimmedUrl;
  }
  
  // Kiểm tra nếu là URL hợp lệ
  if (trimmedUrl.startsWith("http://") || trimmedUrl.startsWith("https://") || trimmedUrl.startsWith("/")) {
    return trimmedUrl;
  }
  
  // Nếu privacy = false, vẫn trả về ảnh nhưng sẽ được blur ở UI
  return trimmedUrl;
};

/**
 * Helper function: Lấy ảnh dựa trên privacy và type
 * @param {string} imageUrl - URL ảnh
 * @param {boolean} privacy - Trạng thái privacy
 * @param {string} type - Loại hiển thị ('list' | 'offer')
 * @returns {string|null} - URL ảnh hoặc null
 */
export const getImageByPrivacyAndType = (imageUrl, privacy = true, type = "list") => {
  return getPetImageByPrivacy(imageUrl, privacy);
};

/**
 * Component MAvatar - Hiển thị avatar pet với hỗ trợ privacy và placeholder
 * @param {Object} props
 * @param {string} props.imageUrl - URL ảnh của pet
 * @param {string} props.image_url - URL ảnh (snake_case, fallback)
 * @param {boolean} props.privacy - Trạng thái privacy (mặc định: true)
 * @param {string} props.type - Loại hiển thị: 'list' (90x90px) hoặc 'offer' (70x70px)
 * @param {string} props.alt - Alt text cho ảnh
 * @param {string} props.className - Class name bổ sung
 */
const MAvatar = ({
  imageUrl,
  image_url,
  privacy = true,
  type = "list",
  alt = "Pet",
  className = "",
}) => {
  // Xử lý cả camelCase và snake_case
  // Kiểm tra cả null, undefined, và empty string
  // Ưu tiên image_url (snake_case) vì database dùng snake_case
  const imgUrl = 
    (image_url && typeof image_url === 'string' && image_url.trim() !== '' && image_url !== 'null') 
      ? image_url
      : (imageUrl && typeof imageUrl === 'string' && imageUrl.trim() !== '' && imageUrl !== 'null')
        ? imageUrl
        : "";
  const finalImageUrl = getImageByPrivacyAndType(imgUrl, privacy, type);
  const hasImage = finalImageUrl && typeof finalImageUrl === 'string' && finalImageUrl.trim() !== "" && finalImageUrl !== "null" && finalImageUrl !== "undefined";
  
  // Debug: Log để kiểm tra (chỉ log khi có vấn đề)
  React.useEffect(() => {
    if (!hasImage && (imageUrl || image_url)) {
      console.warn("MAvatar - Không thể hiển thị ảnh:", {
        imageUrl,
        image_url,
        imgUrl,
        finalImageUrl,
        hasImage,
      });
    }
  }, [imageUrl, image_url, imgUrl, finalImageUrl, hasImage]);

  // Kích thước dựa trên type
  const size = type === "offer" ? 70 : 90;
  const sizeClass = type === "offer" ? "mavatar-offer" : "mavatar-list";

  return (
    <div className={`mavatar-wrapper ${sizeClass} ${className}`}>
      {hasImage ? (
        <img
          className={`mavatar-image ${privacy === false ? "mavatar-blur" : ""}`}
          src={finalImageUrl}
          alt={alt}
          onError={(e) => {
            // Nếu ảnh lỗi, ẩn img và hiển thị placeholder
            e.target.style.display = "none";
            const placeholder = e.target.parentElement.querySelector(
              ".mavatar-placeholder"
            );
            if (placeholder) placeholder.style.display = "flex";
          }}
        />
      ) : null}
      <div
        className="mavatar-placeholder"
        style={{ display: hasImage ? "none" : "flex" }}
      >
        <span className="mavatar-placeholder-text">PetImageNotFound</span>
      </div>
    </div>
  );
};

export default MAvatar;


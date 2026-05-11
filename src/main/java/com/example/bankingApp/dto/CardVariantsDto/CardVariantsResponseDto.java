    package com.example.bankingApp.dto.CardVariantsDto;

    import com.example.bankingApp.entity.CardRequests.CardVariant;

    public class CardVariantsResponseDto {

        private Long cardVariantId;

        private String cardColourFront;

        private String cardColourBack;

        private String chipImage;

        private String textColour;

        public CardVariantsResponseDto(CardVariant cardVariant) {
            this.cardVariantId = cardVariant.getVariantId();
            this.cardColourFront = cardVariant.getCardColourFront();
            this.cardColourBack = cardVariant.getCardColourBack();
            this.chipImage = cardVariant.getChipImage();
            this.textColour = cardVariant.getTextColour();
        }

        public CardVariantsResponseDto() {
        }

        public Long getCardVariantId() {
            return cardVariantId;
        }

        public void setCardVariantId(Long cardVariantId) {
            this.cardVariantId = cardVariantId;
        }

        public String getCardColourFront() {
            return cardColourFront;
        }

        public void setCardColourFront(String cardColourFront) {
            this.cardColourFront = cardColourFront;
        }

        public String getCardColourBack() {
            return cardColourBack;
        }

        public void setCardColourBack(String cardColourBack) {
            this.cardColourBack = cardColourBack;
        }

        public String getChipImage() {
            return chipImage;
        }

        public void setChipImage(String chipImage) {
            this.chipImage = chipImage;
        }

        public String getTextColour() {
            return textColour;
        }

        public void setTextColour(String textColour) {
            this.textColour = textColour;
        }
    }

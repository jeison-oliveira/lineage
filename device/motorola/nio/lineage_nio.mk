#
# Copyright (C) 2022 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit from those products. Most specific first.
$(call inherit-product, $(SRC_TARGET_DIR)/product/core_64_bit.mk)
$(call inherit-product, $(SRC_TARGET_DIR)/product/full_base_telephony.mk)

# Inherit some common Lineage stuff.
$(call inherit-product, vendor/lineage/config/common_full_phone.mk)

# Inherit from nio device
$(call inherit-product, device/motorola/nio/device.mk)

# Device identifier. This must come after all inclusions.
PRODUCT_NAME := lineage_nio
PRODUCT_DEVICE := nio
PRODUCT_BRAND := motorola
PRODUCT_MODEL := XT2125-4
PRODUCT_MANUFACTURER := motorola

PRODUCT_SYSTEM_NAME := nio_retcn

# Build info
BUILD_FINGERPRINT := "motorola/nio_retcn/nio:12/S1RN32.55-16-2/125d6-32b4dd:user/release-keys"
PRODUCT_BUILD_PROP_OVERRIDES += \
    PRIVATE_BUILD_DESC="nio_retcn-user 12 S1RN32.55-16-2 125d6-32b4dd release-keys" \
    TARGET_PRODUCT=$(PRODUCT_SYSTEM_NAME)


#novas configuracoes
PRODUCT_PACKAGES += \
	smartgas_client

BOARD_SEPOLICY_DIRS += device/motorola/nio/sepolicy

# Smartgas AIDL Interface
PRODUCT_PACKAGES += devtitans.smartgas

# Smartgas Binder Service
PRODUCT_PACKAGES += devtitans.smartgas-service

# Device Framework Matrix (Declara que o nosso produto Curupira precisa do serviço smartgas)
DEVICE_FRAMEWORK_COMPATIBILITY_MATRIX_FILE := device/motorola/nio/device_framework_matrix.xml

PRODUCT_PACKAGES += smartgas_service_client

# Manager
PRODUCT_PACKAGES += devtitans.smartgasmanager
# App
PRODUCT_PACKAGES += SmartgasApp

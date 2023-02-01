/*
* Copyright (c) 2016 - 2018, The Linux Foundation. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are
* met:
*   * Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*   * Redistributions in binary form must reproduce the above
*     copyright notice, this list of conditions and the following
*     disclaimer in the documentation and/or other materials provided
*     with the distribution.
*   * Neither the name of The Linux Foundation nor the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
* ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
* BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
* BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
* OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

#ifndef __FORMATS_H__
#define __FORMATS_H__

#include <core/layer_stack.h>

namespace sdm {

struct FormatTileSize {
  /*< Tile width in pixels. For YUV formats this will give only the
      tile width for Y plane*/
  uint32_t tile_width = 0;
  /*< Tile height in pixels. For YUV formats this will give only the
      tile height for Y plane*/
  uint32_t tile_height = 0;

  /*< Tile width in pixels. Only valid for YUV formats where this will
      give tile width for UV plane*/
  uint32_t uv_tile_width = 0;
  /*< Tile height in pixels. Only valid for YUV formats where this will
       give tile height for UV plane*/
  uint32_t uv_tile_height = 0;
};

bool IsUBWCFormat(LayerBufferFormat format);
bool Is10BitFormat(LayerBufferFormat format);
const char *GetFormatString(const LayerBufferFormat &format);
BufferLayout GetBufferLayout(LayerBufferFormat format);
int GetBufferFormatTileSize(LayerBufferFormat format, FormatTileSize *tile_size);
float GetBufferFormatBpp(LayerBufferFormat format);
int GetCwbAlignmentFactor(LayerBufferFormat format);
bool HasAlphaChannel(LayerBufferFormat format);
bool IsWideColor(const ColorPrimaries &color_primary);
bool IsRgbFormat(const LayerBufferFormat &format);

}  // namespace sdm

#endif  // __FORMATS_H__


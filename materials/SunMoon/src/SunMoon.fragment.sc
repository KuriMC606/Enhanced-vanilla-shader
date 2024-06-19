$input v_texcoord0

#include <bgfx_shader.sh>

uniform vec4 SunMoonColor;

SAMPLER2D(s_SunMoonTexture, 0);

void main() {

    vec4 color = texture2D(s_SunMoonTexture, v_texcoord0);
    
    color.rgb *= 1.5*SunMoonColor.rgb;
    
    gl_FragColor = color;
}
$input v_color0, v_fog, v_texcoord0, v_lightmapUV, v_block, wPos

#include <bgfx_shader.sh>
#include <shader/tonemap.h>
#include <shader/sky.h>
#include <shader/lightning.h>

uniform vec4 FogColor;
uniform vec4 SkyColor;

SAMPLER2D(s_MatTexture, 0);
SAMPLER2D(s_SeasonsTexture, 1);
SAMPLER2D(s_LightMapTexture, 2);

void main() {
    vec4 diffuse;
    vec4 color;

#if defined(DEPTH_ONLY_OPAQUE) || defined(DEPTH_ONLY)
    diffuse.rgb = vec3(1.0, 1.0, 1.0);
#else
    diffuse = texture2D(s_MatTexture, v_texcoord0);

#if defined(ALPHA_TEST)
    if (diffuse.a < 0.5) {
        discard;
    }
#endif

#if defined(SEASONS) && (defined(OPAQUE) || defined(ALPHA_TEST))
    diffuse.rgb *=
        mix(vec3(1.0, 1.0, 1.0),
            texture2D(s_SeasonsTexture, v_color0.xy).rgb * 2.0, v_color0.b);
    diffuse.rgb *= v_color0.aaa;
#else
    diffuse *= v_color0;
#endif
color = v_color0;
#endif

#ifndef TRANSPARENT
    diffuse.a = 1.0;
    if(v_block.b > 0.9){
    diffuse.rgb = vec3_splat(1.0 - 2.95*(1.0 - diffuse.b*1.8));
    diffuse.rgb *= vec3(0.71,0.72,1.0);
    diffuse.a *= 0.2;
    diffuse.rgb = getSkyRefl(normalize(wPos.xyz), SkyColor, FogColor);
    }
#endif

    diffuse.rgb *= texture2D(s_LightMapTexture, v_lightmapUV).rgb;

    diffuse.rgb = mix(diffuse.rgb, FogColor.rgb, v_fog.a);
    
    diffuse.rgb = 1.5*diffuse.rgb;
    
    diffuse.rgb = colorCorrection(diffuse.rgb);
    
    gl_FragColor = diffuse;
}
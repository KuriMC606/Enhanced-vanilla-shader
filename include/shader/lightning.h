#ifndef LIGHTNING_H
#define LIGHTNING_H

//#define FOG_AMBIENT
#define BRIGHTNESS 1.3
#define LIGHT_COL vec3(1.0,0.9,0.8)

vec3 Lightning(vec4 fog){

vec3 light;

#ifdef FOG_AMBIENT
light *= fog.rgb;
#endif

vec3 lightCol = LIGHT_COL;

light *= BRIGHTNESS*lightCol;

return light;
}

#endif

#ifndef SKY_H
#define SKY_H

vec3 getSky(vec3 vDir, vec3 sky, vec3 fog){

float gra = smoothstep(1.0,0.0, vDir.y);

vec3 col;

col = mix(sky.rgb, fog.rgb, gra);

return col;
}

vec3 getSkyRefl(vec3 vDir, vec4 sky, vec4 fog){

vDir.y = -vDir.y;

vec3 col;

col = getSky( vDir, sky.rgb, fog.rgb);

return col;
}

#endif

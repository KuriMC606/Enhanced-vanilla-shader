#ifndef TONEMAP_H
#define TONEMAP_H


#define SATURATION 1.6
#define EXPOSURE 4.0
#define TONEMAP_TYPE 3
#define CONSTRAST 1.7


//#define SATURATION 0.8
//#define EXPOSURE 4.0
//#define TONEMAP_TYPE 4
//#define CONSTRAST 1.7

vec3 colorCorrection(vec3 col) {
  #ifdef EXPOSURE
    col *= EXPOSURE;
  #endif

  // ref - https://64.github.io/tonemapping/
  #if TONEMAP_TYPE == 3
    // extended reinhard tonemap
    const float whiteScale = 0.063;
    col = col*(1.0+col*whiteScale)/(1.0+col);
  #elif TONEMAP_TYPE == 4
    // aces tonemap
    const float a = 1.04;
    const float b = 0.03;
    const float c = 0.93;
    const float d = 0.56;
    const float e = 0.14;
    col *= 0.85;
    col = clamp((col*(a*col + b)) / (col*(c*col + d) + e), 0.0, 1.0);
  #elif TONEMAP_TYPE == 2
    // simple reinhard tonemap
    col = col/(1.0+col);
  #elif TONEMAP_TYPE == 1
    // exponential tonemap
    col = 1.0-exp(-col*0.8);
  #endif

  // gamma correction + contrast
  col = pow(col, vec3_splat(CONSTRAST));

  #ifdef SATURATION
    col = mix(vec3_splat(dot(col,vec3(0.21, 0.71, 0.08))), col, SATURATION);
  #endif

  return col;
}

#endif

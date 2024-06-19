#ifdef OPAQUE
$input v_fogColor, v_worldPos, v_PosTime, sPos, v_skyColor
#endif

#include <bgfx_shader.sh>
#include <shader/sky.h>
#include <shader/tonemap.h>
#include <shader/dectect.h>

// Falling Stars code By i11212 : https://www.shadertoy.com/view/mdVXDm

highp float hashS(
	highp vec2 x){
return fract(sin(dot(
	x,vec2(11,57)))*4e3);
	}

highp float star(
	highp vec2 x, float time){
x = mul(x, mtxFromCols(vec2(cos(0.0), sin(0.0)), vec2(sin(0.0), -cos(0.5))));
x.y += time*10.0;
highp float shape = (1.0-length(
	fract(x-vec2(0,0.5))-0.5));
x *= vec2(1,0.1);
highp vec2 fr = fract(x);
highp float random = step(hashS(floor(x)),0.01),
	      	  tall = (1.0-(abs(fr.x-0.5)+fr.y*0.5))*random;
return clamp(clamp((shape-random)*step(hashS(
	floor(x+vec2(0,0.05))),.01),0.0,1.0)+tall,0.0,1.0);
	}

void main() {
#ifdef OPAQUE
  vec3 viewDir = normalize(v_worldPos);
  
  float mask = max(1.0 - 3.0*max(v_fogColor.b, v_fogColor.g), 0.0);

  vec3 skyColor = getSky(viewDir, 0.8*v_skyColor, 1.5*v_fogColor);

  skyColor = colorCorrection(skyColor);
  
  skyColor += pow(vec3_splat(star(sPos.zx*250.0, v_PosTime.w))*1.0, vec3(16,7,5))*mask;

  gl_FragColor = vec4(skyColor, 1.0);
#else
  gl_FragColor = vec4(0.0,0.0,0.0,0.0);
#endif
}

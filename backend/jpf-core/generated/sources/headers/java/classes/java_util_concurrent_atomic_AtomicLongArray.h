/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class java_util_concurrent_atomic_AtomicLongArray */

#ifndef _Included_java_util_concurrent_atomic_AtomicLongArray
#define _Included_java_util_concurrent_atomic_AtomicLongArray
#ifdef __cplusplus
extern "C" {
#endif
#undef java_util_concurrent_atomic_AtomicLongArray_serialVersionUID
#define java_util_concurrent_atomic_AtomicLongArray_serialVersionUID -2308431214976778248LL
/*
 * Class:     java_util_concurrent_atomic_AtomicLongArray
 * Method:    getNative
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_java_util_concurrent_atomic_AtomicLongArray_getNative
  (JNIEnv *, jobject, jint);

/*
 * Class:     java_util_concurrent_atomic_AtomicLongArray
 * Method:    compareAndSetNative
 * Signature: (IJJ)Z
 */
JNIEXPORT jboolean JNICALL Java_java_util_concurrent_atomic_AtomicLongArray_compareAndSetNative
  (JNIEnv *, jobject, jint, jlong, jlong);

#ifdef __cplusplus
}
#endif
#endif
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#include <assert.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>
#include "src/global.h"
#include "Highlight.h"

/*jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    return JNI_VERSION_1_6;
}*/

void msg(JNIEnv *env, char *str);
void msg(JNIEnv *env, char *str)
{

	jclass Exception = (*env)->FindClass(env, "java/lang/Exception");
	(*env)->ThrowNew(env, Exception, str);

}

/*
 * Class:     com_jecelyin_highlight_Highlight
 * Method:    read_file
 * Signature: (Ljava/lang/String;)[B
 */
/*
JNIEXPORT jbyteArray JNICALL Java_com_jecelyin_highlight_Highlight_read_1file(JNIEnv *env, jclass cls, jstring file)
{
	char *buffer;
	const char *path;
	jboolean iscopy;

	path = (*env)->GetStringUTFChars(env, file, &iscopy);
	if(path == NULL)
	{
		return NULL;
	}
	//int ret = read_file(path, &buffer);
	int ret=-1;
	buffer = read_file3(path, &ret);
	(*env)->ReleaseStringUTFChars(env, file, path);

	if (ret == -1) {
		jclass Exception = (*env)->FindClass(env, "java/lang/Exception");
		(*env)->ThrowNew(env, Exception, "error: Can't read file.");
		return NULL;
	}

	jbyteArray jb;
	jb = (*env)->NewByteArray(env, ret);
	(*env)->SetByteArrayRegion(env, jb, 0, ret, (jbyte *) buffer);
	_free(buffer);

	return jb;
}*/

/*
 * Class:     com_jecelyin_highlight_Highlight
 * Method:    jni_parse
 * Signature: (Ljava/lang/String;Ljava/lang/String;)[[I
 */


JNIEXPORT jintArray JNICALL jniparse(JNIEnv *env, jclass cls, jstring text, jstring syntaxfile)
{
	LOGV("jni parse");
	int textfilesize;
	const char *textbufConst;
	jboolean iscopy=0;
	char *memopenedfile;
	char *memsyntaxfile;
	int synfilesize;
	const char *syntaxfileConst;
	int len;
	//转换源代码格式
	textbufConst = (*env)->GetStringUTFChars(env, text, &iscopy);
	if(textbufConst == NULL)
	{
		LOGV("text null");
		return NULL;
	}

/*	FILE *fd = NULL;
	fd = fopen("/mnt/sdcard/xim.log", "w");
	fwrite(textbufConst, 1, strlen(textbufConst), fd);
	fclose(fd);*/

	//语法文件路径
	syntaxfileConst = (*env)->GetStringUTFChars(env, syntaxfile, &iscopy);
	if(syntaxfileConst == NULL)
	{
		(*env)->ReleaseStringUTFChars(env, text, textbufConst);
		LOGV("syntaxfile null");
		return NULL;
	}

	int retLen=0;
	int *ret;
	//解析语法
	LOGV("txtlen: %i", STRLEN(textbufConst));
	ret = read_syntax(syntaxfileConst, textbufConst, &retLen);
	(*env)->ReleaseStringUTFChars(env, text, textbufConst);
	(*env)->ReleaseStringUTFChars(env, syntaxfile, syntaxfileConst);
	LOGV("retLen: %i", retLen);
	if(ret == NULL || retLen < 1)
	{
		return NULL;
	}

	jintArray parseIntArray = (*env)->NewIntArray(env, retLen);
	if(parseIntArray == NULL)
	{
		return NULL;
	}
	(*env)->SetIntArrayRegion(env, parseIntArray, 0, retLen, (jint *) ret);
	_free(ret);
	//(*env)->DeleteLocalRef(env, parseRet);
	return parseIntArray;

}

JNIEXPORT jint JNICALL test(JNIEnv *env, jclass cls)
{
	return 1;
}

static JNINativeMethod gMethods[] = {
		{"test","()I",(void*)test},
		{"jniparse","(Ljava/lang/String;Ljava/lang/String;)[I",(void*)jniparse}

};


#define JNIREG_CLASS "org/bybbs/jume/highlight"
static int registerNativeMethods(JNIEnv * env,const char* className,JNINativeMethod* getMethods,int numMethods){
	jclass clazz;
	clazz = (*env)->FindClass(env,className);
	if(clazz == NULL)
		return JNI_FALSE;
	if((*env)->RegisterNatives(env,clazz,gMethods,numMethods)<0)
		return JNI_FALSE;

	return JNI_TRUE;
}

static int registerNatives(JNIEnv* env){
	if(!registerNativeMethods(env,JNIREG_CLASS,gMethods,sizeof(gMethods)/sizeof(gMethods[0]))){
		return JNI_FALSE;
	}
	return JNI_TRUE;
}
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm,void* reserved)
{
	JNIEnv* env = NULL;
	jint result = -1;
	if((*vm)->GetEnv(vm,(void**)&env,JNI_VERSION_1_2)!=JNI_OK){
		__android_log_print(ANDROID_LOG_ERROR,"tag","load libary error 1");
		return JNI_ERR;
	}
	assert(env != NULL);
	if(!registerNatives(env)){
		__android_log_print(ANDROID_LOG_ERROR,"tag","load libary error 2");
	}

	result = JNI_VERSION_1_2;
	__android_log_print(ANDROID_LOG_ERROR,"tag","load libaray success: %d",result);
	return result;

}





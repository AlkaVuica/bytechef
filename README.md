# PIPER

A distributed, fault-tolerant pipeline execution engine. 

# Why another one?

I've been looking around for some time now for a workflow engine for distributed processing. While there are several candidates, none seemed to be designed from the ground up for distributed processing on a per-task basis. 

# How it works? 

Piper works by executing a set of tasks defined as a YAML document. 

An hello world example might look like this: 

```
name: Simple video transcoding

input: 
  - name: video
    value: /path/to/input.mov
    required: true
    
tasks: 
  - name: Generate a random alpahnumeric string
    handler: random
    mode: alpahnumeric
    returns: randomString

  - name: Create a temporary directory
    handler: mkdir
    path: ${tmpdir}/${randomString}
    returns: workDir

  - name: Transcode the video
    handler: ffmpeg
    input: ${video}
    preset: video_only
    output: ${workDir}/video.mp4

  - name: Transcode the audio
    handler: ffmpeg
    input: ${video}
    preset: audio_only
    output: ${workDir}/audio.mp4
    
  - name: Mux the audio and video
    handler: ffmpeg
    input: 
           - ${workDir}/video.mp4
           - ${workDir}/audio.mp4
    preset: mux
    output: ${workDir}/final.mp4    
```
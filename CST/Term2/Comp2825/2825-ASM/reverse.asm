section .bss
    buf    resb 128
    rev    resb 128

section .text
    global _start

_start:
; Input (Just like I mentioned in the chat)
    mov     eax, 3              ; System call 3: sys_read
    mov     ebx, 0              ; File descriptor 0: stdin
    mov     ecx, buf            ; Pointer to input buffer
    mov     edx, 128            ; Read up to 128 bytes
    int     0x80                ; Trigger interrupt

; Exit if len < 1
    cmp     eax, 1              ; CoMPare EAX with 1
    jl      do_exit             ; Jump to do_exit if Less

; Delete \n
    mov     ebx, eax            ; Save len to EBX
    mov     ecx, buf            ; Save ptr to ECX
    add     ecx, ebx            ; Goto end of ptr
    dec     ecx                 ; Point to last char
    mov     al, [ecx]           ; Put the last char into AL
    cmp     al, 0x0A            ; Check if \n
    jne     skip_strip          ; Jump to skip_strip if Not Equal \n
    dec     ebx                 ; Len -1 if \n

; Reverse
skip_strip:
   xor     esi, esi            ; Clear ESI, efficiently using XOR

rev_loop:
   cmp     esi, ebx            ; while(esi < ebx)
   jge     write_out           ; If ESI > or = EBX: break
   ; Copy len from EBX because EBX is the condition
   mov     ecx, ebx
   dec     ecx                 ; Move ptr forward 1 char
   sub     ecx, esi            ; Skip chars that already copied
   ; Save that char, use AL (lower byte for EAX) for 1 byte
   mov     al, [buf + ecx]
   mov     [rev + esi], al     ; Write from left to right
   inc     esi                 ; esi++
   jmp     rev_loop

; Output
write_out:
    mov     eax, 4              ; System call 4: sys_write
    mov     ebx, 1              ; File descriptor 1: stdout
    mov     ecx, rev            ; Pointer to rev
    mov     edx, esi            ; Length of output
    int     0x80

do_exit:
    mov     eax, 1              ; System call 1: sys_exit
    xor     ebx, ebx            ; return 0
    int     0x80
pow:
    movq $1, %rax;
    cmpq $0, %rsi;
    je ret;
    jmp loop;
loop:
    mulq %rdi;
    subq $1, %rsi;
    je ret;
    jmp loop;
ret:
    retq;

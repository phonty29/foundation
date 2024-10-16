min:
    // Put your code here
    movq %rdi, %rax;
    cmpq %rdi, %rsi;
    ja rdi_less;
    movq %rsi, %rax;
rdi_less:
    retq;
